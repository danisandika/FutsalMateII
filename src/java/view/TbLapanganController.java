package view;

import model.TbLapangan;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbLapanganFacade;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("tbLapanganController")
@SessionScoped
public class TbLapanganController implements Serializable {

    private TbLapangan current;
    private DataModel items = null;
    @EJB
    private controller.TbLapanganFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TbLapanganController() {
    }

    public TbLapangan getSelected() {
        if (current == null) {
            current = new TbLapangan();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbLapanganFacade getFacade() {
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (TbLapangan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareView2() {
        current = (TbLapangan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewLapangan";
    }

    public String prepareCreate() {
        current = new TbLapangan();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.setStatus(1);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbLapanganCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbLapangan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbLapanganUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy(Integer id) {
        current = ejbFacade.getTbLapanganByIDLapangan(id);
        performDestroy();
        return viewLapanganByIDFutsal(current.getIdFutsal().getIdFutsal());
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
            current.setStatus(0);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("swal('Berhasil Dihapus')");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Gagal menghapus data");
        }
    }
    
    private void performActivate() {
        try {
            current.setStatus(1);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbLapanganDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
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

    public TbLapangan getTbLapangan(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = TbLapangan.class)
    public static class TbLapanganControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbLapanganController controller = (TbLapanganController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbLapanganController");
            return controller.getTbLapangan(getKey(value));
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
            if (object instanceof TbLapangan) {
                TbLapangan o = (TbLapangan) object;
                return getStringKey(o.getIdLapangan());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TbLapangan.class.getName());
            }
        }

    }
    
    //Controller buatan sendiri
    
    private List<TbLapangan> listLapangan; 
    private List<TbLapangan> filterLapangan;

    public List<TbLapangan> getFilterLapangan() {
        return filterLapangan;
    }

    public void setFilterLapangan(List<TbLapangan> filterLapangan) {
        this.filterLapangan = filterLapangan;
    }
    
    
    public String viewLapanganByIDFutsal(Integer id){
        listLapangan = ejbFacade.getLapanganByIDFutsal(id);
        return "ListLapangan";
    }
    
    public List<TbLapangan> getListLapangan() {
        return listLapangan;
    }

    public void setListLapangan(List<TbLapangan> listLapangan) {
        this.listLapangan = listLapangan;
    }

    public TbLapangan getCurrent() {
        return current;
    }

    public void setCurrent(TbLapangan current) {
        this.current = current;
    }
       

}
