package view;

import model.TbKonfirmasi;
import model.TbPemesanan;
import model.TbPengelola;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbKonfirmasiFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
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
import javax.servlet.http.Part;


@Named("tbKonfirmasiController")
@SessionScoped
public class TbKonfirmasiController implements Serializable {


    private TbKonfirmasi current;
    private DataModel items = null;
    @EJB private controller.TbKonfirmasiFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TbKonfirmasiController() {
    }

    public TbKonfirmasi getSelected() {
        if (current == null) {
            current = new TbKonfirmasi();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbKonfirmasiFacade getFacade() {
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
        current = (TbKonfirmasi)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    TbPemesanan idPesanan;

    public TbPemesanan getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(TbPemesanan idPesanan) {
        this.idPesanan = idPesanan;
    }
    
    TbPemesananController controlPemesanan;
    
    public String prepareCreate(TbPemesanan pesanan) {
        current = new TbKonfirmasi();
        idPesanan = pesanan;
        selectedItemIndex = -1;
        return "CreateKonfirmasiPayment";
    }

    public String create() {
        
        try {
            ejbFacade.setStatusPesanan(idPesanan.getIdPemesanan(), 1);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed Confirm Payment : "+e.toString());
        }
        
        try {
            upload();
            current.setIdPemesanan(idPesanan);
            current.setFotoTransfer(url);
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Confirm a Payment has been sent");
            
            sendEmail(current);
            idPesanan = null;
            return "Profil";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed to confirm a payment");
            return null;
        }
    }
    
    private Part foto;
    private String url;

    public Part getFoto() {
        return foto;
    }

    public void setFoto(Part foto) {
        this.foto = foto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String upload() {
        try {
            InputStream in = foto.getInputStream();
            setFoto(foto);
            
            url = foto.getSubmittedFileName();
            File f = new File("D://github//FutsalMateII//web//Image_bukti_tranfer_pemain//" + foto.getSubmittedFileName());
            f.createNewFile();
            
            FileOutputStream out = new FileOutputStream(f);
            try (InputStream input = foto.getInputStream()) {
                Files.copy(input, new File("D://github//FutsalMateII//web//Image_bukti_tranfer_pemain//" + foto.getSubmittedFileName()).toPath());
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
    
    private void sendEmail(TbKonfirmasi confirm) {
        MailController mctr = new MailController();
        
        mctr.setFromEmail("pendekarbayangan66@gmail.com");
        mctr.setUsername("pendekarbayangan66@gmail.com");
        mctr.setPassword("praditya");
        mctr.setSubject("Futsalan.com | Confirm Payment by Player");
        
        TbPengelola pengelola = ejbFacade.getDataPengelola(confirm.getIdPemesanan().getIdLapangan().getIdFutsal().getIdFutsal());
        
        mctr.setToMail(pengelola.getEmail());
        mctr.setMessage("You get paid for the rental of the field. "
                + "Please check the website and confirm the payment has been received "
                + "to the player so they can immediately play on your field");
        mctr.send();
    }

    public String prepareEdit() {
        current = (TbKonfirmasi)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbKonfirmasiUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TbKonfirmasi)getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbKonfirmasiDeleted"));
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

    public TbKonfirmasi getTbKonfirmasi(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbKonfirmasi.class)
    public static class TbKonfirmasiControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbKonfirmasiController controller = (TbKonfirmasiController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbKonfirmasiController");
            return controller.getTbKonfirmasi(getKey(value));
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
            if (object instanceof TbKonfirmasi) {
                TbKonfirmasi o = (TbKonfirmasi) object;
                return getStringKey(o.getIdKonfirmasi());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbKonfirmasi.class.getName());
            }
        }

    }

}
