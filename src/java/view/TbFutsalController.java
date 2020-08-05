package view;

import model.TbFutsal;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbFutsalFacade;

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
import model.TbLapangan;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@Named("tbFutsalController")
@SessionScoped
public class TbFutsalController implements Serializable {

    private TbFutsal current;
    private DataModel items = null;
    @EJB
    private controller.TbFutsalFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private UploadedFile uploadFoto;

    public UploadedFile getUploadFoto() {
        return uploadFoto;
    }

    public void setUploadFoto(UploadedFile uploadFoto) {
        this.uploadFoto = uploadFoto;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        JsfUtil.addSuccessMessage("File Upload Successfully");
    }
    
    public void upload() {
        if (uploadFoto != null) {
            JsfUtil.addSuccessMessage("File Upload Successfully : "+uploadFoto.getFileName());
        }
    }

    public TbFutsalController() {
    }

    public TbFutsal getSelected() {
        if (current == null) {
            current = new TbFutsal();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbFutsalFacade getFacade() {
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
        current = (TbFutsal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbFutsal();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbFutsalCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbFutsal) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Data Berhasil di Update");
            return "View_Futsal";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Data Gagal di Update : "+e.toString());
            return null;
        }
    }

    public String destroy() {
        current = (TbFutsal) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbFutsalDeleted"));
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

    public TbFutsal getTbFutsal(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = TbFutsal.class)
    public static class TbFutsalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbFutsalController controller = (TbFutsalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbFutsalController");
            return controller.getTbFutsal(getKey(value));
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
            if (object instanceof TbFutsal) {
                TbFutsal o = (TbFutsal) object;
                return getStringKey(o.getIdFutsal());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TbFutsal.class.getName());
            }
        }

    }

    /////////////////////////    PENGELOLA FUTSAL   ////////////////////////////////////
    private List<TbLapangan> listlapangan;
    public void getFutsal(String id) {
        current = new TbFutsal();
        current = ejbFacade.getFutsal(Integer.valueOf(id));
    }
          

    public TbFutsal getCurrent() {
        return current;
    }

    public void setCurrent(TbFutsal current) {
        this.current = current;
    }

    

}
