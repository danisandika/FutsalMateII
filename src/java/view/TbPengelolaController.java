package view;

import model.TbPengelola;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbPengelolaFacade;

import java.io.Serializable;
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


@Named("tbPengelolaController")
@SessionScoped
public class TbPengelolaController implements Serializable {


    private TbPengelola current;
    private DataModel items = null;
    @EJB private controller.TbPengelolaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<TbPengelola> filterPengelola;
    private List<TbPengelola> listPengelola;

    public List<TbPengelola> getListPengelola() {
        return listPengelola;
    }

    public void setListPengelola(List<TbPengelola> listPengelola) {
        this.listPengelola = listPengelola;
    }
    
    
    

    public List<TbPengelola> getFilterPengelola() {
        return filterPengelola;
    }

    public void setFilterPengelola(List<TbPengelola> filterPengelola) {
        this.filterPengelola = filterPengelola;
    }
    
    

    public TbPengelolaController() {
    }

    public TbPengelola getSelected() {
        if (current == null) {
            current = new TbPengelola();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbPengelolaFacade getFacade() {
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

    public String prepareViewAdmin() {
        current = (TbPengelola)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewPengelola";
    }

    public String prepareView() {
        current = (TbPengelola)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbPengelola();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPengelolaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbPengelola)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPengelolaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TbPengelola)getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPengelolaDeleted"));
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

    public TbPengelola getTbPengelola(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbPengelola.class)
    public static class TbPengelolaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbPengelolaController controller = (TbPengelolaController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbPengelolaController");
            return controller.getTbPengelola(getKey(value));
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
            if (object instanceof TbPengelola) {
                TbPengelola o = (TbPengelola) object;
                return getStringKey(o.getIdPengelola());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbPengelola.class.getName());
            }
        }

    }

    
    
    
    //////////////////////////////////////////////// HAHA ////////////////////////////////////////////////
    
    
    public String ubahStatus() {
        current = (TbPengelola) getItems().getRowData();
        try {
            if (current.getStatus() == 0) {
                getFacade().ubahStatus(current, 1);
            } else if (current.getStatus() == 1) {
                getFacade().ubahStatus(current, 0);
            }
            JsfUtil.addSuccessMessage("Status Pengelola berhasil diubah");
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        
        return "ListPengelola";
    }
    
    private Date currentDate = new Date();

    public Date getCurrentDate() {
        return currentDate;
    }
    
    public String nonAktifPengelola(){
        listPengelola = ejbFacade.listNonAktifPengelola(currentDate);
        return "nonakPengelola";
    }
    
    
    public String nonaktifkan(Integer id){
         try {
            
            current = getFacade().getDataPengelola(id);
            MailController mctr = new MailController();
       
            mctr.setFromEmail("pendekarbayangan66@gmail.com");
            mctr.setUsername("pendekarbayangan66@gmail.com");
            mctr.setPassword("praditya");
            mctr.setSubject("Pembayaran Sewa Site Berhasil");
            mctr.setToMail(current.getEmail());
            mctr.setMessage("Dear,"+current.getIdFutsal().getNamaFutsal()+"\nMasa Sewa Website Anda berakhir pada tanggal "+current.getTglBerakhir()+" Silahkan melakukan transaksi penyewaan Site untuk dapat membuka kembali website anda. \n Terima Kasih");
            
            current.setStatus(0);
            getFacade().ubahStatusSewaBerakhir(current.getIdFutsal().getIdFutsal());
            getFacade().edit(current);
            mctr.send();
            JsfUtil.addSuccessMessage("Sewa Pengelola berhasil diakhiri");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Gagal : "+e.toString());
        }
         return "nonakPengelola";
    }
    
}
