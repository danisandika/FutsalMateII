package view;

import model.TbMatchteam;
import model.TbTeam;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbMatchteamFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import loginPackage.SessionUtils;


@Named("tbMatchteamController")
@SessionScoped
public class TbMatchteamController implements Serializable {


    private TbMatchteam current;
    private DataModel items = null;
    @EJB private controller.TbMatchteamFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TbMatchteamController() {
    }

    public TbMatchteam getSelected() {
        if (current == null) {
            current = new TbMatchteam();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbMatchteamFacade getFacade() {
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
        current = (TbMatchteam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbMatchteam();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbMatchteamCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbMatchteam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbMatchteamUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TbMatchteam)getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbMatchteamDeleted"));
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

    public TbMatchteam getTbMatchteam(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbMatchteam.class)
    public static class TbMatchteamControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbMatchteamController controller = (TbMatchteamController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbMatchteamController");
            return controller.getTbMatchteam(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TbMatchteam) {
                TbMatchteam o = (TbMatchteam) object;
                return getStringKey(o.getIdMatchteam());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbMatchteam.class.getName());
            }
        }

    }

    
    
    
    ///////////////////////////////////////////////////////////////////////////// Do your thang /////////////////
    

    public String prepareCreateByPemain() {
        current = new TbMatchteam();
        selectedItemIndex = -1;
        return "CreateMatch";
    }

    public String createMatch() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            
            HttpSession session = SessionUtils.getSession();
            
            current.setIdMatchteam(formatter.format(date));
            current.setIdHomeTeam((TbTeam) session.getAttribute("templateIdTeam"));
            
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbMatchteamCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    
    private List<TbMatchteam> matchByTeam;
    private List<TbMatchteam> matchByHomeTeam;

    public List<TbMatchteam> getMatchByTeam(TbTeam idTeam) {
        return matchByTeam = ejbFacade.getMatchByTeam(idTeam.getIdTeam());
    }

    public void setMatchByTeam(List<TbMatchteam> matchByTeam) {
        this.matchByTeam = matchByTeam;
    }

    public List<TbMatchteam> getMatchByHomeTeam(TbTeam idTeam) {
        return matchByHomeTeam = ejbFacade.getMatchByHomeTeam(idTeam.getIdTeam());
    }

    public void setMatchByHomeTeam(List<TbMatchteam> matchByHomeTeam) {
        this.matchByHomeTeam = matchByHomeTeam;
    }

    public String prepareViewMatchTrans() {
        current = (TbMatchteam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewDetailMatchCap";
    }

    public String prepareViewMatch() {
        current = (TbMatchteam)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewDetailMatch";
    }
    
    public String pleaseLogin() {
        return "SignIn";
    }
    
    public String joinMatchTeam() {          // Kalo login
        HttpSession session = SessionUtils.getSession();
        TbTeam team = (TbTeam) session.getAttribute("templateIdTeam");
        
        try {
            current.setIdAwayTeam(team);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Join Match with Your Team Success");
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Join Match with Your Team Failed");
        }
        
        return "ViewDetailMatch";
    }
    
}
