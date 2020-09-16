package view;

import model.TbTeam;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbTeamFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import loginPackage.SessionUtils;

import model.TbPemain;


@Named("tbTeamController")
@SessionScoped
public class TbTeamController implements Serializable {


    private TbTeam current;
    private DataModel items = null;
    @EJB private controller.TbTeamFacade ejbFacade;
    @EJB private controller.TbPemainFacade ejbPemainFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private TbTeam teamCaptain;
    private List<TbPemain> pemainByTeam;
    private List<TbPemain> filterPemainTeam;
    
    private Part logo;
    private String url;
    
    TbPemainController pemainController;

    public TbTeamController() {
    }

    public Part getLogo() {
        return logo;
    }

    public void setLogo(Part logo) {
        this.logo = logo;
    }

    public List<TbPemain> getFilterPemainTeam() {
        return filterPemainTeam;
    }

    public void setFilterPemainTeam(List<TbPemain> filterPemainTeam) {
        this.filterPemainTeam = filterPemainTeam;
    }

    public List<TbPemain> getPemainByTeam() {
        return pemainByTeam = new ArrayList<>(teamCaptain.getTbPemainCollection());
    }

    public TbTeam getTeamCaptain() {
        HttpSession session = SessionUtils.getSession();
        TbTeam myTeam = (TbTeam) session.getAttribute("templateIdTeam");
        return teamCaptain = ejbFacade.getByID(myTeam);
    }

    public void setTeamCaptain(TbTeam teamCaptain) {
        this.teamCaptain = teamCaptain;
    }

    public TbTeam getSelected() {
        if (current == null) {
            current = new TbTeam();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbTeamFacade getFacade() {
        return ejbFacade;
    }
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (TbTeam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbTeam();
        selectedItemIndex = -1;
        return "xCreate";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbTeamCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbTeam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    public void deleteFile(String urlLogoLama) {
        File f = new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_logo_team//" + urlLogoLama);
        f.delete();
    }

    public String update() {
        try {
            if (logo == null) {
                getFacade().edit(current);
            } else {
                upload();
                deleteFile(current.getLogo());
                current.setLogo(url);
                getFacade().edit(current);
            }
                JsfUtil.addSuccessMessage("Your team information has been successfully edited");
                recreateModel();
                return "ManageTeam";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Your team information failed to edit");
            return null;
        }
    }

    public String destroy() {
        current = (TbTeam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbTeamDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public TbTeam getTbTeam(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbTeam.class)
    public static class TbTeamControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbTeamController controller = (TbTeamController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbTeamController");
            return controller.getTbTeam(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TbTeam) {
                TbTeam o = (TbTeam) object;
                return getStringKey(o.getIdTeam());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbTeam.class.getName());
            }
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////// Harus gila untuk tetap waras di dunia yg gila /////////////////////////
    

    public String prepareViewAdmin() {
        current = (TbTeam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewTeam";
    }

    public String prepareCreateByPemain() {
        current = new TbTeam();
        selectedItemIndex = -1;
        return "CreateTeam";
    }

    public String createTeam() {
        try {
            upload();
            HttpSession session = SessionUtils.getSession();
            TbPemain player = (TbPemain) session.getAttribute("templateDataPemain");
            current.setLogo(url);
            current.setLose(0);
            current.setRate(0);
            current.setWin(0);
            current.setCaptain(player);
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Your team has been created");
            recreateModel();
            
            ejbPemainFacade.joinTeam(current, player.getIdPemain());
            
            session.setAttribute("templateIsCaptain", true);
            session.setAttribute("templateIdTeam", current);
            session.setAttribute("templateDataPemain", player);
            
            return "ListTeam";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Your team has failed to create");
            return null;
        }
    }

    public String prepareListPemain() {
        recreateModel();
        return "ListTeam";
    }

    public String prepareViewTeam() {
        current = (TbTeam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewTeam";
    }

    public String prepareViewTeam(Integer team) {
        current = ejbFacade.getByID2(team);
        return "UserPemain/ViewTeam";
    }
    
    public String joinTeam() {          // Kalo login
        HttpSession session = SessionUtils.getSession();
        Integer idPemain = (Integer) session.getAttribute("templateIDPemain");
        
        current = (TbTeam)getItems().getRowData();
        try {
            ejbPemainFacade.joinTeam(current, idPemain);
            session.setAttribute("templateIdTeam", current.getIdTeam());
            JsfUtil.addSuccessMessage("Join Team Success");
            sendEmailJoinTeam(current);
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Join Team Failed");
        }
        
        return "ListTeam";
    }
    
    public String joinTeam2() {          // Kalo login
        HttpSession session = SessionUtils.getSession();
        Integer idPemain = (Integer) session.getAttribute("templateIDPemain");
        
        try {
            ejbPemainFacade.joinTeam(current, idPemain);
            session.setAttribute("templateIdTeam", current.getIdTeam());
            JsfUtil.addSuccessMessage("Join Team Success");
            sendEmailJoinTeam(current);
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Join Team Failed");
        }
        
        return "ViewTeam";
    }
    
    public void sendEmailJoinTeam(TbTeam team) {
        
        MailController mctr = new MailController();
        
        mctr.setFromEmail("pendekarbayangan66@gmail.com");
        mctr.setUsername("pendekarbayangan66@gmail.com");
        mctr.setPassword("praditya");
        mctr.setSubject("Futsalan.com | New Member Team");
        
        mctr.setToMail(team.getCaptain().getEmail());
        mctr.setMessage("<b>Hello Captain " + team.getCaptain().getNama() + " !</b>"
                + "<br/>Your team " + team.getNamaTeam()
                + "got new players, check now on the website.");
        mctr.send();
    }
    
    public String pleaseLogin() {
        return "SignIn";
    }

    public String prepareEditMyTeam(TbTeam myTeam) {
        current = myTeam;
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "EditTeam";
    }
    
    public String upload() {
        try {
            InputStream in = logo.getInputStream();
            setLogo(logo);
            
            File f = new File("D://github//2.3//FutsalMateII//web//Image_logo_team//" + logo.getSubmittedFileName());
            f.createNewFile();
            
            url = logo.getSubmittedFileName();
            FileOutputStream out = new FileOutputStream(f);
            try (InputStream input = logo.getInputStream()) {
                Files.copy(input, new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_logo_team//" + logo.getSubmittedFileName()).toPath());
            } catch (IOException e) {
                
            }
            
            byte[] buffer = new byte[1024];
            int length;
            
            while((length = in.read(buffer)) > 0) {
                out.write(buffer);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return url;
    }

    
    List<TbTeam> listTop4Team;

    public List<TbTeam> getListTop4Team() {
        return listTop4Team = ejbFacade.getTop4Team();
    }

    public void setListTop4Team(List<TbTeam> listTop4Team) {
        this.listTop4Team = listTop4Team;
    }
}
