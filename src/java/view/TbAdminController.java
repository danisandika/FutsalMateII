package view;

import model.TbAdmin;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbAdminFacade;

import java.io.Serializable;
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


@Named("tbAdminController")
@SessionScoped
public class TbAdminController implements Serializable {


    private TbAdmin current;
    private DataModel items = null;
    @EJB private controller.TbAdminFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<TbAdmin> filterAdmin;

    public List<TbAdmin> getFilterAdmin() {
        return filterAdmin;
    }

    public void setFilterAdmin(List<TbAdmin> filterAdmin) {
        this.filterAdmin = filterAdmin;
    }
    
    

    public TbAdminController() {
    }

    public TbAdmin getSelected() {
        if (current == null) {
            current = new TbAdmin();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbAdminFacade getFacade() {
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
        return "ListAdmin";
    }

    public String prepareView() {
        current = (TbAdmin)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewAdmin";
    }

    public String prepareCreate() {
        current = new TbAdmin();
        selectedItemIndex = -1;
        return "ListAdmin";
    }

    public String create() {
        
        boolean EmailExists = getFacade().getEmailAdminNotExist(current.getEmail());
        
        if(EmailExists == false){
        try {
            current.setStatus(1);
            current.setPassword("futsalan");
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Sukses Menambahkan Administrator");
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Gagal Menambahkan Administrator : "+e.toString());
            return null;
        }
        }else{
            JsfUtil.addErrorMessage("Email yang digunakan sudah ada di Website");
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbAdmin)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Data Berhasil di Update");
            return "ListAdmin";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Data Gagal di Update : "+e.toString());
            return null;
        }
    }

    public String destroy() {
        //current = (TbAdmin)getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        //recreatePagination();
        //recreateModel();
        return "ListAdmin";
    }
    
    public String aktif() {
        //current = (TbAdmin)getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performAktif();
        //recreatePagination();
        //recreateModel();
        return "ListAdmin";
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
            JsfUtil.addSuccessMessage("Admin Berhasil Di hapus");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Admin Gagal di hapus : "+e.toString());
        }
    }
    
    private void performAktif() {
        try {
            current.setStatus(1);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Admin Berhasil di Aktifkan");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Admin Berhasil di Aktifkan : "+e.toString());
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

    public TbAdmin getTbAdmin(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbAdmin.class)
    public static class TbAdminControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbAdminController controller = (TbAdminController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbAdminController");
            return controller.getTbAdmin(getKey(value));
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
            if (object instanceof TbAdmin) {
                TbAdmin o = (TbAdmin) object;
                return getStringKey(o.getIdAdmin());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbAdmin.class.getName());
            }
        }

    }
}
