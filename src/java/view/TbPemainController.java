package view;

import model.TbPemain;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbPemainFacade;

import java.util.List;
import java.io.Serializable;
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
import model.TbTeam;


@Named("tbPemainController")
@SessionScoped
public class TbPemainController implements Serializable {


    private TbPemain current;
    private DataModel items = null;
    @EJB private controller.TbPemainFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<TbPemain> pemainByTeam;
    private List<TbPemain> filterFutsal;

    public TbPemainController() {
    }

    public List<TbPemain> getFilterFutsal() {
        return filterFutsal;
    }

    public void setFilterFutsal(List<TbPemain> filterFutsal) {
        this.filterFutsal = filterFutsal;
    }

    
    
    
    
    public TbPemain getSelected() {
        if (current == null) {
            current = new TbPemain();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbPemainFacade getFacade() {
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
        current = (TbPemain)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbPemain();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPemainCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbPemain)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPemainUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TbPemain)getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPemainDeleted"));
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

    public TbPemain getTbPemain(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbPemain.class)
    public static class TbPemainControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbPemainController controller = (TbPemainController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbPemainController");
            return controller.getTbPemain(getKey(value));
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
            if (object instanceof TbPemain) {
                TbPemain o = (TbPemain) object;
                return getStringKey(o.getIdPemain());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbPemain.class.getName());
            }
        }

    }

    /////////////////////////////////////////// Admin  ///////////////////////////////////////////////////////////////
    

    public String prepareViewAdmin() {
        current = (TbPemain)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewPemain";
    }
    
    
    /////////////////////////////////////////////////////////////// PEMAIN CAPTAIN TEAM /////////////////////////////
    
    public String removeFromTeam(TbPemain player) {          // Kalo login
        player.setIdTeam(null);
        
        try {
            getFacade().edit(player);
            recreateModel();
            JsfUtil.addSuccessMessage("Success kick your member team");
            return "index";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed kick your member team");
            return null;
        }
    }
    
    /////////////////////////////////////////////////////////////////////// PEMAINNYA SENDIRI ////////////////////////
    
    
    public String prepareEditPemain() {
        HttpSession session = SessionUtils.getSession();
        current = (TbPemain) session.getAttribute("templateDataPemain");
        return "Profil";
    }
}
