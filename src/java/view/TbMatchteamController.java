package view;

import model.TbMatchteam;
import model.TbTeam;
import model.TbPemain;
import model.TbIndividuMatch;
import model.TbPemesanan;
import view.util.JsfUtil;
import view.util.PaginationHelper;
import controller.TbMatchteamFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    private String idPesanBayangan;

    public String getIdPesanBayangan() {
        return idPesanBayangan;
    }

    public void setIdPesanBayangan(String idPesanBayangan) {
        this.idPesanBayangan = idPesanBayangan;
    }

    public String prepareEditMatch(TbMatchteam match) {
        current = match;
        return "EditMatch";
    }

    public String updateMatch1() {
        if (!idPesanBayangan.equals("")) {
            boolean isReserved, isPesanConfirm;
            // ngecek idPesan e kii tenan wes dipesen urung?
            isPesanConfirm = ejbFacade.getAutentikasiPemesananConfrim(idPesanBayangan);

            if (isPesanConfirm) {
                try {
                    TbPemesanan objPemesanan = ejbFacade.getDataPemesanan(idPesanBayangan);

                    current.setIdPemesanan(objPemesanan);

                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage("Update a Match for your team success");
                    idPesanBayangan = "";
                    recreateModelMatchByHomeTeam();
                    return "ManageTeam";
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Update a Match for your team failed");
                    return null;
                }
            } else {
                idPesanBayangan = "";
                JsfUtil.addErrorMessage("Your ID Reservation was not found, or make sure the reservation is confirmed by the field owner");
                return "ManageTeam";
            }
            
        } else {
            try {
                getFacade().edit(current);
                JsfUtil.addSuccessMessage("Update a Match for your team success, don't forget to Reserve Field");
                recreateModelMatchByHomeTeam();
                return "ManageTeam";
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Update a Match for your team failed");
                return null;
            }
        }
    }

    public String updateMatch2() {
            try {
                getFacade().edit(current);
                JsfUtil.addSuccessMessage("Update a Match for your team success");
                recreateModelMatchByHomeTeam();
                return "ManageTeam";
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Update a Match for your team failed");
                return null;
            }
    }
    
    public String createMatch() {
        if (!idPesanBayangan.equals("")) {
            boolean isReserved, isPesanConfirm;
            // ngecek idPesan e kii tenan wes dipesen urung?
            isPesanConfirm = ejbFacade.getAutentikasiPemesananConfrim(idPesanBayangan);

            if (isPesanConfirm) {   // yen pesanane enek + status 2 true
                // yen iki ngecek idne kii wes pernah dinggo po ra
                isReserved = ejbFacade.getAutentikasiPemesanan(idPesanBayangan);
                if (!isReserved) { // yen pesanane durung dinggo
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date();
                        
                        HttpSession session = SessionUtils.getSession();
                        
                        TbPemesanan objPemesanan = ejbFacade.getDataPemesanan(idPesanBayangan);
                        
                        current.setIdPemesanan(objPemesanan);
                        current.setIdMatchteam(formatter.format(date));
                        current.setIdHomeTeam((TbTeam) session.getAttribute("templateIdTeam"));

                        getFacade().create(current);
                        JsfUtil.addSuccessMessage("Create a Match for your team success");
                        recreateModel();
                        idPesanBayangan = "";
                        return "ListMatch";
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Create a Match for your team failed");
                        return null;
                    }
                } else {
                    JsfUtil.addErrorMessage("Your ID Reservation has been use");
                    return "CreateMatch";
                }
            } else {
                JsfUtil.addErrorMessage("Your ID Reservation was not found, or make sure the reservation is confirmed by the field owner");
                return "CreateMatch";
            }
            
        } else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();

                HttpSession session = SessionUtils.getSession();

                current.setIdMatchteam(formatter.format(date));
                current.setIdHomeTeam((TbTeam) session.getAttribute("templateIdTeam"));

                getFacade().create(current);
                JsfUtil.addSuccessMessage("Create a Match for your team success, don't forget to Reserve Field");
                recreateModel();
                idPesanBayangan = "";
                return "ListMatch";
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Create a Match for your team failed");
                return null;
            }
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


    private void recreateModelMatchByHomeTeam() {
        matchByHomeTeam = null;
    }
    
    public List<TbMatchteam> getMatchByHomeTeam(TbTeam idTeam) {
        return matchByHomeTeam = ejbFacade.getMatchByHomeTeam(idTeam.getIdTeam());
    }

    public void setMatchByHomeTeam(List<TbMatchteam> matchByHomeTeam) {
        this.matchByHomeTeam = matchByHomeTeam;
    }

    TbMatchteam matchCek;

    public TbMatchteam getMatchCek() {
        return matchCek;
    }

    public void setMatchCek(TbMatchteam matchCek) {
        this.matchCek = matchCek;
    }
    
    public String prepareViewMatchTrans(TbMatchteam match) {
        matchCek = match;
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
    
    public String joinMatchIndividu() {          // Kalo login
        HttpSession session = SessionUtils.getSession();
        TbPemain pemain = (TbPemain) session.getAttribute("templateDataPemain");
        
        boolean isJoin = false;
        isJoin = ejbFacade.getAutentikasiIndividuMatch(current.getIdMatchteam(), pemain.getIdPemain());
        
        if (!isJoin) {
            if (!pemain.getIdTeam().getIdTeam().equals(current.getIdHomeTeam().getIdTeam())) {
                if (!pemain.getIdTeam().getIdTeam().equals(current.getIdAwayTeam().getIdTeam())) {
                    try {
                        ejbFacade.joinMatchIndividu(current, pemain);
                        JsfUtil.addSuccessMessage("Join Match Success");
                        recreateModelIndividuMatch();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Join Match Failed");
                    }
                } else {
                    JsfUtil.addErrorMessage("Your team has joined in this match, as away team");
                }
            } else {
                JsfUtil.addErrorMessage("Your team has joined in this match, as home team");
            }
        } else {
            JsfUtil.addErrorMessage("You have joined this match");
        }
        
        
        return "ViewDetailMatch";
    }
    
    List<TbIndividuMatch> listIndividuMatch;

    private void recreateModelIndividuMatch() {
        listIndividuMatch = null;
    }

    public List<TbIndividuMatch> getListIndividuMatch(TbMatchteam match) {
        return listIndividuMatch = ejbFacade.getMatchIndividu(match);
    }

    public void setListIndividuMatch(List<TbIndividuMatch> listIndividuMatch) {
        this.listIndividuMatch = listIndividuMatch;
    }
    

    public String prepareEditConfirmMatch(TbMatchteam match) {
        if (match.getIdAwayTeam() == null) {
            JsfUtil.addErrorMessage("Your team has no opponents, you cannot confirm the match for your win");
            return "ManageTeam";
        } else {
            current = match;
            return "EditMatchConfirmResult";
        }
    }

    public String updateConfirmMatch() {
        Integer resultHome, resultAway;
        
        resultHome = current.getHomeScore();
        resultAway = current.getAwayScore();
        
        ////////////////////////////////////////////////////////////////////////////////////// Update home&away TbTeam
        ////////////////////////////////////////////////////////////////////////////////////// HOME TEAM
        try {
            //////////////////////////////////////////////////////////////////// GET DATA HOME TEAM
            TbTeam homeTeam = ejbFacade.cekDataTeam(current.getIdHomeTeam().getIdTeam());
            Integer winHome = homeTeam.getWin();
            Integer loseHome = homeTeam.getLose();
            
            if (resultHome > resultAway) {
                winHome = homeTeam.getWin() + 1;
            } else if (resultHome < resultAway) {
                loseHome = homeTeam.getLose() + 1;
            } else if (resultHome == resultAway) {
                winHome = homeTeam.getWin() + 1;
            }
            
            ejbFacade.setResultMatchTeam(winHome, loseHome, current.getIdHomeTeam().getIdTeam());
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed to update data homeTeam");
        }
        
        ////////////////////////////////////////////////////////////////////////////////////// AWAY TEAM
        try {
            //////////////////////////////////////////////////////////////////// GET DATA HOME TEAM
            TbTeam awayTeam = ejbFacade.cekDataTeam(current.getIdAwayTeam().getIdTeam());
            Integer winAway = awayTeam.getWin();
            Integer loseAway = awayTeam.getLose();

            if (resultAway > resultHome) {
                winAway = awayTeam.getWin() + 1;
            } else if (resultAway < resultHome) {
                loseAway = awayTeam.getLose() + 1;
            } else if (resultAway == resultHome) {
                winAway = awayTeam.getWin() + 1;
            }
            
            ejbFacade.setResultMatchTeam(winAway, loseAway, current.getIdAwayTeam().getIdTeam());
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed to update data awayTeam");
        }
        
        //////////////////////////////////////////////////////////////////////////////////////////// Update tbMatchTeam
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Thank you for your Confirmation");
            recreateModelMatchByHomeTeam();
            return "ManageTeam";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Your Confirmation failed");
            return null;
        }
    }
}
