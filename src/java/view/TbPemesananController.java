package view;

import model.TbPemesanan;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbPemesananFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
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
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.objects.NativeString;
import loginPackage.SessionUtils;
import model.TbTeam;
import model.TbKonfirmasi;
import model.TbLapangan;
import model.TbPemain;
import model.TbPengelola;


@Named("tbPemesananController")
@SessionScoped
public class TbPemesananController implements Serializable {


    private TbPemesanan current;
    private DataModel items = null;
    @EJB private controller.TbPemesananFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Date dateAwal;
    private Date dateAkhir;
    private Date currentDate = new Date();
    private TbPengelola tbPengelola;
    
    public TbPemesananController() {
    }

    public TbPemesanan getSelected() {
        if (current == null) {
            current = new TbPemesanan();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Date getDateAwal() {
        return dateAwal;
    }

    public void setDateAwal(Date dateAwal) {
        this.dateAwal = dateAwal;
    }

    public Date getDateAkhir() {
        return dateAkhir;
    }

    public void setDateAkhir(Date dateAkhir) {
        this.dateAkhir = dateAkhir;
    }

    public Date getCurrentDate() {
        return currentDate;
    }
    
    
    public void reportlistKonfirmasi(){
        tbPengelola = ejbFacade.getPengelolaFutsal(loginPackage.SessionUtils.getId());
        listKonfirmasi = ejbFacade.getListKonfirmasi(tbPengelola.getIdFutsal().getIdFutsal());
    }
    
    
    public void filterByDate(){
        tbPengelola = ejbFacade.getPengelolaFutsal(loginPackage.SessionUtils.getId());
        if(dateAwal == null){
            dateAwal = getCurrentDate();
        }
        listKonfirmasi = ejbFacade.getPemesananFilterByDate(dateAwal, dateAkhir,tbPengelola.getIdFutsal().getIdFutsal());
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
            MailController mctr = new MailController();
            mctr.setFromEmail("pendekarbayangan66@gmail.com");
            mctr.setUsername("pendekarbayangan66@gmail.com");
            mctr.setPassword("praditya");
            mctr.setSubject("Pembayaran Pemesanan Futsal Berhasil");
            mctr.setToMail(current.getIdPemain().getEmail());
            mctr.setMessage("Selamat,Pemesanan Lapangan "+current.getIdLapangan().getNamaLapangan()+" telah di Konfirmasi oleh Pengelola Futsal."
                    + " Silahkan bermain Futsal Sesuai Jadwal yang telah Anda tentukan dan tetap gunakan Layanan dari Futsalan. \n Terima Kasih");

            current.setStatus(2);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Sukses Konfirmasi Pemesanan");
            mctr.send();
            return "listPemesanan";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Konfirmasi Pemesanan Gagal : "+e.toString());
            return null;
        }
    }
    
    public String tolakPemesanan() {
        try {
            MailController mctr = new MailController();
            mctr.setFromEmail("pendekarbayangan66@gmail.com");
            mctr.setUsername("pendekarbayangan66@gmail.com");
            mctr.setPassword("praditya");
            mctr.setSubject("Pembayaran Pemesanan Futsal di Tolak");
            mctr.setToMail(current.getIdPemain().getEmail());
            mctr.setMessage("Maaf,Pemesanan Lapangan "+current.getIdLapangan().getNamaLapangan()+" telah di Tolak oleh Pengelola Futsal."
                    + " karena terindikasi penipuan. Info lebih lanjut silahkan hubungi Pengelola Futsal terkait. \n Terima Kasih");

            current.setStatus(4);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Sukses Konfirmasi Pemesanan");
            mctr.send();
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
    private List<TbKonfirmasi> listKonfirmasi;

    public List<TbKonfirmasi> getListKonfirmasi() {
        
        return listKonfirmasi;
    }

    public void setListKonfirmasi(List<TbKonfirmasi> listKonfirmasi) {
        this.listKonfirmasi = listKonfirmasi;
    }
    
    
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
    Date datetimePlay;

    public Date getDatetimePlay() {
        return datetimePlay;
    }

    public void setDatetimePlay(Date datetimePlay) {
        this.datetimePlay = datetimePlay;
    }

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
        return "CreateReservasiLap";
    }
    
    public String createReservasi() {
        try {
            HttpSession session = SessionUtils.getSession();
            SimpleDateFormat formatID = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            
            current.setIdPemesanan(formatID.format(date));
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
            
            SimpleDateFormat formatTglMain = new SimpleDateFormat("dd/MM/yyyy");
            current.setTglMain(datetimePlay);
            current.setJamMainMulai(datetimePlay);
            
            SimpleDateFormat formatJamMulai = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String ubah = formatJamMulai.format(datetimePlay);
            
            String hari = NativeString.substr(ubah, 0, 10);
            String menit = NativeString.substring(ubah, 13);
            
            int jam = datetimePlay.getHours();
            int count = jam + current.getDurasi();
            
            String temp = hari +" "+ Integer.toString(count) + menit;
            
            Date jamSelesai = formatJamMulai.parse(temp);
            current.setJamMainSelesai(jamSelesai);
            
            getFacade().create(current);
            sendEmail(current);
            JsfUtil.addSuccessMessage("Your Reservation Success, please check your E-mail to continue your payment");
            return "ViewLapangan";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed to Reservation");
            return null;
        }
    }
    
    public void updateStatus(TbPemesanan pesanan, Integer stat) {
        try {
            pesanan.setStatus(stat);
            getFacade().edit(pesanan);
        } catch (Exception e) {
        }
    }
    
    private void sendEmail(TbPemesanan pesanan) {
        SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatJam = new SimpleDateFormat("HH:mm");
        MailController mctr = new MailController();
        Locale locale = new Locale("in", "ID");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        
        int total = pesanan.getIdLapangan().getHarga() * pesanan.getDurasi();
        
        mctr.setFromEmail("pendekarbayangan66@gmail.com");
        mctr.setUsername("pendekarbayangan66@gmail.com");
        mctr.setPassword("praditya");
        mctr.setSubject("Futsalan.com | Invoice Reservation Field");
        mctr.setToMail(pesanan.getIdPemain().getEmail());
        mctr.setMessage("<b>Note Payment</b> <br/>"
                + "<br/> ID Reservation : " + pesanan.getIdPemesanan()
                + "<br/> Field : " + pesanan.getIdLapangan().getNamaLapangan() + ", Futsal " + pesanan.getIdLapangan().getIdFutsal().getNamaFutsal()
                + "<br/> In the name of : " + pesanan.getIdPemain().getNama()
                + "<br/> Date Reservation : " + formatTgl.format(pesanan.getTglPemesanan())
                + "<br/> Date Play : " + formatTgl.format(pesanan.getTglMain())
                + "<br/> Time Play : " + formatJam.format(pesanan.getJamMainMulai()) + " - " + formatJam.format(pesanan.getJamMainSelesai()) + " WIB (" + pesanan.getDurasi() + " Hour)"
                + "<br/><br/> <b>Total payment : " + currencyFormatter.format(total) + "</b><br/>"
                + "Kirimkan ke " + pesanan.getIdLapangan().getIdFutsal().getNamaRekening()
                + "( " + pesanan.getIdLapangan().getIdFutsal().getNamaBank()
                + " a.n " + pesanan.getIdLapangan().getIdFutsal().getNamaRekening()
                + " )<br/><br/> Please make a payment then confirm payment via Futsalan.com website"
                + "<br/> Thank You for Your Reservation");
        mctr.send();
    }
    
    private List<TbPemesanan> pemesananByPemain;

    public void recreateListPemesananByPemain() {
        pemesananByPemain = null;
    }
    
    public List<TbPemesanan> getPemesananByPemain(TbPemain player) {
        return pemesananByPemain = ejbFacade.getPemesananByIDPemain(player.getIdPemain());
    }

    public void setPemesananByPemain(List<TbPemesanan> reservByTeam) {
        this.pemesananByPemain = reservByTeam;
    }
    
}
