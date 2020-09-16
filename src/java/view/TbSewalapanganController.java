package view;

import model.TbSewalapangan;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbSewalapanganFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import loginPackage.SessionUtils;
import model.TbAdmin;
import model.TbBank;
import model.TbFutsal;

@Named("tbSewalapanganController")
@SessionScoped
public class TbSewalapanganController implements Serializable {

    private TbSewalapangan current;
    private TbFutsal Futsal;
    private DataModel items = null;
    @EJB
    private controller.TbSewalapanganFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idFutsal;
    private String url;
    private Part gambar;
    private Date dateAwal;
    private Date dateAkhir;

    public TbSewalapanganController() {
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
    
    public void filterByDate(){
        if(dateAwal == null){
            dateAwal = getCurrentDate();
        }
        System.out.println(dateAwal.toString());
        listSewa = ejbFacade.getSewaFilterByDate(dateAwal, dateAkhir);
    }

    public TbSewalapangan getSelected() {
        if (current == null) {
            current = new TbSewalapangan();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TbSewalapanganFacade getFacade() {
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
        current = (TbSewalapangan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TbSewalapangan();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current = new TbSewalapangan();
            String idSewa = ejbFacade.getLastIDSewa();
            Futsal = ejbFacade.getFutsalByIDFutsal(idFutsal);
            current.setIdSewalapangan(idSewa);
            current.setIdFutsal(Futsal);
            current.setIdBank(bank);
            current.setTglSewa(currentDate);
            current.setTglBerakhir(getCurrentDatePlus(waktuSewa));
            current.setWaktuSewa(waktuSewa);
            current.setJumlahUang(jumlahUang);
            current.setStatusBayar(0);
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Data Berhasil disimpan");
            CreateNull();
            return "listSewaWeb";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Data Gagal disimpan : "+e.toString());
            return null;
        }
    }


    public void CreateNull(){
        current = new TbSewalapangan();
        current.setIdSewalapangan(null);
        current.setIdFutsal(null);
        waktuSewa= null;
        jumlahUang = null;
        current.setIdBank(null);
        current.setTglSewa(null);
        current.setWaktuSewa(null);
        current.setJumlahUang(null);
        current.setStatusBayar(0);
    }

    public String prepareEdit() {
        current = (TbSewalapangan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbSewalapanganUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TbSewalapangan) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TbSewalapanganDeleted"));
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

    public TbSewalapangan getTbSewalapangan(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = TbSewalapangan.class)
    public static class TbSewalapanganControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TbSewalapanganController controller = (TbSewalapanganController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tbSewalapanganController");
            return controller.getTbSewalapangan(getKey(value));
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
            if (object instanceof TbSewalapangan) {
                TbSewalapangan o = (TbSewalapangan) object;
                return getStringKey(o.getIdSewalapangan());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TbSewalapangan.class.getName());
            }
        }

    }



    //METHOD ORIGINAL BY DANIS
    private Date currentDate = new Date();
    private List<TbSewalapangan> listSewa;
    private List<TbSewalapangan> filterSewa;
    private List<TbBank> listBank;
    private TbBank bank;
    private Integer waktuSewa;
    private Integer jumlahUang;
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final DateTimeFormatter dateFormat8 = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public Integer getWaktuSewa() {
        return waktuSewa;
    }

    public void setWaktuSewa(Integer waktuSewa) {
        this.waktuSewa = waktuSewa;
    }

    public Integer getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(Integer jumlahUang) {
        this.jumlahUang = jumlahUang;
    }



    public List<TbSewalapangan> getListSewa() {
        return listSewa;
    }

    public void setListSewa(List<TbSewalapangan> listSewa) {
        this.listSewa = listSewa;
    }

    public List<TbSewalapangan> getFilterSewa() {
        return filterSewa;
    }

    public void setFilterSewa(List<TbSewalapangan> filterSewa) {
        this.filterSewa = filterSewa;
    }

    public TbSewalapangan getCurrent() {
        return current;
    }

    public void setCurrent(TbSewalapangan current) {
        this.current = current;
    }

    public List<TbBank> getListBank() {
        return listBank;
    }

    public void setListBank(List<TbBank> listBank) {
        this.listBank = listBank;
    }

    public void reportSewa(){
        listSewa = ejbFacade.getListSewa();
    }

    public void getSewaLapanganbyID(Integer id){
        idFutsal = id;
        listSewa = ejbFacade.getLapanganByID(id);
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Date getCurrentDatePlus(Integer year){
        // convert date to localdatetime
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("localDateTime : " + dateFormat8.format(localDateTime));

        // plus one
        localDateTime = localDateTime.plusYears(year);


        // convert LocalDateTime to date
        Date currentDatePlusYear = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return currentDatePlusYear;
    }

    public void sumTotalSewa(){
        jumlahUang = waktuSewa * 300000;
    }

    public TbFutsal getFutsal() {
        return Futsal;
    }

    public void setFutsal(TbFutsal Futsal) {
        this.Futsal = Futsal;
    }


    public void cblistBank(){
        listBank = ejbFacade.getBank();
    }

    public TbBank getBank() {
        return bank;
    }

    public void setBank(TbBank bank) {
        this.bank = bank;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Part getGambar() {
        return gambar;
    }

    public void setGambar(Part gambar) {
        this.gambar = gambar;
    }

     public String upload() {

        try {
            InputStream in = gambar.getInputStream();
            setGambar(gambar);
            File f = new File("D://github//2.3//FutsalMateII//web//Image_web_bukti_transfer//" + gambar.getSubmittedFileName());
            f.createNewFile();
//            url = f.toString();
            url = gambar.getSubmittedFileName();
            FileOutputStream out = new FileOutputStream(f);
            try (InputStream input = gambar.getInputStream()) {
                Files.copy(input, new File("C://Users//Danis//Desktop//PRG7//FutsalMateII//web//Image_web_bukti_transfer//" + gambar.getSubmittedFileName()).toPath());
            } catch (IOException e) {
                // Show faces message?
            }
            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer);
            }
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String prepareViewAdmin() {
        current = (TbSewalapangan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "ViewSewaLap";
    }

    public String confirmPay(Integer id) {
        //current = (TbSewalapangan) getItems().getRowData();
        TbAdmin admin = ejbFacade.getAdminByID(id);
        MailController mctr = new MailController();
        String emailPengelola = ejbFacade.getEmailPengellaFromFutsal(current.getIdFutsal().getIdFutsal());
        
        
        mctr.setFromEmail("pendekarbayangan66@gmail.com");
        mctr.setUsername("pendekarbayangan66@gmail.com");
        mctr.setPassword("praditya");
        mctr.setSubject("Pembayaran Sewa Site Berhasil");
        mctr.setToMail(emailPengelola);
        mctr.setMessage("Selamat,"+current.getIdFutsal().getNamaFutsal()+" Pembayaran Anda Berhasil, Silahkan Cek Email anda dan Login ke dalam Site Pengelola. \n Terima Kasih");
        
        try{
            
            ejbFacade.ubahStatusPengelola(current.getIdFutsal().getIdFutsal(),current.getTglBerakhir());
            //ejbFacade.ubahStatusSewaBerakhir(current.getIdFutsal().getIdFutsal());
        }catch(Exception e){
            JsfUtil.addErrorMessage("Gagal Mengkonfirmasi Pembayaran : "+e.toString());
        }
        
        
        try {
            current.setStatusBayar(2);
            current.setIdAdmin(admin);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Pembayaran Terkonfirmasi");
            recreatePagination();
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage( "Gagal Mengkonfirmasi Pembayaran : "+e.toString());
        }
        
        mctr.send();
        return "ListSewaLap";
    }

    
    public String tolakSewa(Integer id) {
        //current = (TbSewalapangan) getItems().getRowData();
        TbAdmin admin = ejbFacade.getAdminByID(id);
        MailController mctr = new MailController();
        String emailPengelola = ejbFacade.getEmailPengellaFromFutsal(current.getIdFutsal().getIdFutsal());
        
        
        mctr.setFromEmail("pendekarbayangan66@gmail.com");
        mctr.setUsername("pendekarbayangan66@gmail.com");
        mctr.setPassword("praditya");
        mctr.setSubject("Pembayaran Sewa Site di Tolak");
        mctr.setToMail(emailPengelola);
        mctr.setMessage("Maaf ,"+current.getIdFutsal().getNamaFutsal()+" Pembayaran Anda di Tolak oleh Administrator Futsalan, dikarenakan terindikasi Penipuan oleh Sistem. Silahkan balas email ini untuk informasi lebih lanjut. \n Terima Kasih");
        
        
        
        try {
            current.setStatusBayar(4);
            current.setIdAdmin(admin);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Pembayaran di Tolak");
            recreatePagination();
            recreateModel();
        } catch (Exception e) {
            JsfUtil.addErrorMessage( "Gagal Mengkonfirmasi Pembayaran : "+e.toString());
        }
        
        mctr.send();
        return "ListSewaLap";
    }
    
    
    public String batasSewaHabis(){
        Date sekarang = currentDate;
        String batas = ejbFacade.getBatasSewaAkhir(sekarang);
        return batas;
    }
    
    
    public String konfirmasiSewa(String id){
        current = ejbFacade.getSewaByID(id);
        return "konfirmasiSewaWeb";
    }

    public String konfirmasi() {
        try {
            upload();
            current.setTglPembayaran(currentDate);
            current.setBuktiTransfer(url);
            current.setStatusBayar(1);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Upload Bukti Transfer Berhasil, Silahkan Tunggu Konfirmasi dari Administrator");
            return "listSewaWeb";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Upload Bukti Transfer Gagal : "+e.toString());
            return null;
        }
    }
}
