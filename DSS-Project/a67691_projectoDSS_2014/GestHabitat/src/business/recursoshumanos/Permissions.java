package business.recursoshumanos;

/**Classe que agrupa permissões de interação com a aplicação e de acesso ao dados.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2015.01.05
 */

class Permissions implements IPermissions{
    
    //Variáveis de instância
    private final String vedit, vcreate, vdelete, vconsult;
    private final String fedit, fcreate, fdelete, fconsult;
    private final String dedit, dcreate, ddelete, dconsult;
    private final String oedit, ocreate, odelete, oconsult;
    private final String sedit, screate, sdelete, sconsult;
    private final String eedit, ecreate, edelete, econsult;
    
    public Permissions(){
        this.vedit="EDIT_VOLUNTARIOS_PERMISSIONS";
        this.vcreate="CREATE_VOLUNTARIOS_PERMISSIONS";
        this.vdelete="DELETE_VOLUNTARIOS_PERMISSIONS";
        this.vconsult="CONSULT_VOLUNTARIOS_PERMISSIONS";
        
        this.fedit="EDIT_FAMILIAS_PERMISSIONS";
        this.fcreate="CREATE_FAMILIAS_PERMISSIONS";
        this.fdelete="DELETE_FAMILIAS_PERMISSIONS";
        this.fconsult="CONSULT_FAMILIAS_PERMISSIONS";
        
        this.dedit="EDIT_DOACOES_PERMISSIONS";
        this.dcreate="CREATE_DOACOES_PERMISSIONS";
        this.ddelete="DELETE_DOACOES_PERMISSIONS";
        this.dconsult="CONSULT_DOACOES_PERMISSIONS";
        
        this.oedit="EDIT_OBRAS_PERMISSIONS";
        this.ocreate="CREATE_OBRAS_PERMISSIONS";
        this.odelete="DELETE_OBRAS_PERMISSIONS";
        this.oconsult="CONSULT_OBRAS_PERMISSIONS";
        
        this.sedit="EDIT_STOCK_PERMISSIONS";
        this.screate="CREATE_STOCK_PERMISSIONS";
        this.sdelete="DELETE_STOCK_PERMISSIONS";
        this.sconsult="CONSULT_STOCK_PERMISSIONS";
        
        this.eedit="EDIT_EVETOS_PERMISSIONS";
        this.ecreate="CREATE_EVETOS_PERMISSIONS";
        this.edelete="DELETE_EVETOS_PERMISSIONS";
        this.econsult="CONSULT_EVETOS_PERMISSIONS";       
    }
    
    /*gets*/
    @Override
    public String getVedit() {return vedit;}
    @Override
    public String getVcreate() {return vcreate;}
    @Override
    public String getVdelete() {return vdelete;}
    @Override
    public String getVconsult() {return vconsult;}
    @Override
    public String getFedit() {return fedit;}
    @Override
    public String getFcreate() {return fcreate;}
    @Override
    public String getFdelete() {return fdelete;}
    @Override
    public String getFconsult() {return fconsult;}
    @Override
    public String getDedit() {return dedit;}
    @Override
    public String getDcreate() {return dcreate;}
    @Override
    public String getDdelete() {return ddelete;}
    @Override
    public String getDconsult() {return dconsult;}
    @Override
    public String getOedit() {return oedit;}
    @Override
    public String getOcreate() {return ocreate;}
    @Override
    public String getOdelete() {return odelete;}
    @Override
    public String getOconsult() {return oconsult;}
    @Override
    public String getSedit() {return sedit;}    
    @Override
    public String getScreate() {return screate;}
    @Override
    public String getSdelete() {return sdelete;}
    @Override
    public String getSconsult() {return sconsult;}
    @Override
    public String getEedit() {return eedit;}    
    @Override
    public String getEcreate() {return ecreate;}
    @Override
    public String getEdelete() {return edelete;}
    @Override
    public String getEconsult() {return econsult;}
}
