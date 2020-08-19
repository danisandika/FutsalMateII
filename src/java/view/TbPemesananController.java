package view;

import model.TbPemesanan;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbPemesananFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import jdk.nashorn.internal.objects.NativeString;
import loginPackage.SessionUtils;
import model.TbTeam;
import model.TbKonfirmasi;
import model.TbLapangan;
import model.TbPemain;


@Named("tbPemesananController")
@SessionScoped
public class TbPemesananController implements Serializable {


    private TbPemesanan current;
    private DataModel items = null;
    @EJB private controller.TbPemesananFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TbPemesananController() {
    }

    public TbPemesanan getSelected() {
        if (current == null) {
            current = new TbPemesanan();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbPemesananFacade getFacade() {
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
        current = (TbPemesanan)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbPemesanan();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPemesananCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TbPemesanan)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.setStatus(2);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Sukses Konfirmasi Pemesanan");
            return "listPemesanan";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Konfirmasi Pemesanan Gagal : "+e.toString());
            return null;
        }
    }

    public String destroy() {
        current = (TbPemesanan)getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPemesananDeleted"));
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

    public TbPemesanan getTbPemesanan(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass=TbPemesanan.class)
    public static class TbPemesananControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbPemesananController controller = (TbPemesananController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbPemesananController");
            return controller.getTbPemesanan(getKey(value));
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
            if (object instanceof TbPemesanan) {
                TbPemesanan o = (TbPemesanan) object;
                return getStringKey(o.getIdPemesanan());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+TbPemesanan.class.getName());
            }
        }

    }
    
    
    
    /////////////////////////    PENGELOLA FUTSAL   ////////////////////////////////////
    private List<TbPemesanan> listPemesanan;
    private List<TbPemesanan> filterPemesanan;
    private TbKonfirmasi tbkonfirmasi;
 
    
    public void getPemesanan(String id,Integer sts) {
        listPemesanan = ejbFacade.getPemesanan(Integer.valueOf(id),sts);
    }
    
    public void getRiwayatPemesanan(String id) {
        listPemesanan = ejbFacade.getRiwayatPemesanan(Integer.valueOf(id));
    }
    
    public String konfPemesanan(String id){
        //current = new TbPemesanan();
        current = ejbFacade.getPemesananByIDPemesanan(id);
        tbkonfirmasi = ejbFacade.getKonfirmasiByIDPemesanan(id);
        return "konfirmasiPemesanan";
    }

    public List<TbPemesanan> getListPemesanan() {
        return listPemesanan;
    }

    public void setListPemesanan(List<TbPemesanan> listPemesanan) {
        this.listPemesanan = listPemesanan;
    }
    
    public List<TbPemesanan> getFilterPemesanan() {
        return filterPemesanan;
    }

    public void setFilterPemesanan(List<TbPemesanan> filterPemesanan) {
        this.filterPemesanan = filterPemesanan;
    }

    public TbPemesanan getCurrent() {
        return current;
    }

    public void setCurrent(TbPemesanan current) {
        this.current = current;
    }

    public TbKonfirmasi getTbkonfirmasi() {
        return tbkonfirmasi;
    }

    public void setTbkonfirmasi(TbKonfirmasi tbkonfirmasi) {
        this.tbkonfirmasi = tbkonfirmasi;
    }
    
    
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////// ceritanya pemainnya mau reservasi lapnya
    
    
    
    TbLapangan lapReserv;

    public TbLapangan getLapReserv() {
        return lapReserv;
    }

    public void setLapReserv(TbLapangan lapReserv) {
        this.lapReserv = lapReserv;
    }
    
    public String prepareReservasi(TbLapangan revLap) {
        lapReserv = revLap;
        current = new TbPemesanan();
        selectedItemIndex = -1;
        return "ReservasiLapangan";
    }
    
    public String createReservasi() {
        try {
            HttpSession session = SessionUtils.getSession();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            
            current.setIdPemesanan(formatter.format(date));
            current.setIdLapangan(lapReserv);
            current.setIdPemain((TbPemain) session.getAttribute("templateDataPemain"));
            current.setTglPemesanan(date);
            current.setStatus(0);
            
            // Catatan
            // yg masih blm bisa jam mulai gamenya, blm dapet nilai yg bener
            // kalo udh dpt nilai yg bener, tinggal gmn caranya nambah ke durassinya
            // 
            // Spekulasi1 : ambil jamnya tambahin, trus balikin lg
            //          kemungkiinan bakal haris ubh ke string > substr > tambah sama durasi > string + gabung > date
            
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String ubah = format2.format(current.getJamMainMulai());
            Date jamMulai = format2.parse(ubah);
            
            String hari = NativeString.substr(ubah, 0, 9);
            String menit = NativeString.substring(ubah, 12);
            
            int jam = jamMulai.getHours();
            int count = jam + current.getDurasi();
            
            String temp = hari + Integer.toString(count) + menit;
            Date jamSelesai = format2.parse(temp);
            current.setJamMainSelesai(jamSelesai);
            
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbPemesananCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    
    private List<TbPemesanan> pemesananByPemain;

    public List<TbPemesanan> getPemesananByPemain(TbPemain player) {
        return pemesananByPemain = ejbFacade.getPemesananByIDPemain(player.getIdPemain());
    }

    public void setPemesananByPemain(List<TbPemesanan> reservByTeam) {
        this.pemesananByPemain = reservByTeam;
    }
    
}
