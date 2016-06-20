package client.gui;

import client.Connection;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import middleware.PDU;
import middleware.SO;

/**
 * Perfil do utilizador, portal para os jogos e outras funcionalidades da aplicação.
 * @author jdc
 * @version 08.04.2015
 */

public class Profile extends javax.swing.JInternalFrame {
    public static JDesktopPane desktop;
    public static Connection con;
    private String username;
    private Home parentFrame;
    
    public Profile(Home home, JDesktopPane desk, Connection con, String username) {
        initComponents();
        
        // Background Image
        this.background.setIcon(new ImageIcon(SO.getFancyBackground()));
        
        // Limitar input em campos hora, minutos, segundos
        JTextFieldLimit limit1 = new JTextFieldLimit(2);
        JTextFieldLimit limit2 = new JTextFieldLimit(2);
        JTextFieldLimit limit3 = new JTextFieldLimit(2);
        
        this.hoursTextField.setDocument(limit1);
        this.minTextField.setDocument(limit2);
        this.secTextField.setDocument(limit3);
        
        this.desktop=desk;
        this.con = con;
        this.username=username;
        
        this.parentFrame = home;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logOutButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        hoursTextField = new javax.swing.JTextField();
        minTextField = new javax.swing.JTextField();
        secTextField = new javax.swing.JTextField();
        nameChallTextField = new javax.swing.JTextField();
        createTextButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        challengesTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        acpChallengesTable = new javax.swing.JTable();
        listChallengeButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        rankingButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        acpChallengeButton = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(843, 569));
        getContentPane().setLayout(null);

        logOutButton.setText("Log out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });
        getContentPane().add(logOutButton);
        logOutButton.setBounds(770, 510, 69, 30);

        helpButton.setText("Ajuda");
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        getContentPane().add(helpButton);
        helpButton.setBounds(10, 510, 61, 30);
        getContentPane().add(dateChooser);
        dateChooser.setBounds(80, 100, 150, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Criar Desafio");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 20, 120, 17);

        hoursTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoursTextFieldActionPerformed(evt);
            }
        });
        getContentPane().add(hoursTextField);
        hoursTextField.setBounds(80, 150, 36, 30);
        getContentPane().add(minTextField);
        minTextField.setBounds(130, 150, 34, 30);
        getContentPane().add(secTextField);
        secTextField.setBounds(180, 150, 36, 30);

        nameChallTextField.setPreferredSize(new java.awt.Dimension(20, 20));
        getContentPane().add(nameChallTextField);
        nameChallTextField.setBounds(80, 50, 150, 30);

        createTextButton.setText("Criar Desafio");
        createTextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTextButtonActionPerformed(evt);
            }
        });
        getContentPane().add(createTextButton);
        createTextButton.setBounds(90, 210, 110, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nome");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 50, 50, 15);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 110, 50, 15);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hora");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 150, 40, 15);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(":");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(120, 160, 20, 14);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText(":");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(170, 160, 20, 14);

        challengesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Data", "Hora", "Criador"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(challengesTable);
        if (challengesTable.getColumnModel().getColumnCount() > 0) {
            challengesTable.getColumnModel().getColumn(0).setResizable(false);
            challengesTable.getColumnModel().getColumn(1).setResizable(false);
            challengesTable.getColumnModel().getColumn(2).setResizable(false);
            challengesTable.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(320, 40, 460, 142);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Desafios disponíveis");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(320, 10, 180, 17);

        acpChallengesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Data", "Hora", "Criador"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(acpChallengesTable);
        if (acpChallengesTable.getColumnModel().getColumnCount() > 0) {
            acpChallengesTable.getColumnModel().getColumn(0).setResizable(false);
            acpChallengesTable.getColumnModel().getColumn(1).setResizable(false);
            acpChallengesTable.getColumnModel().getColumn(2).setResizable(false);
            acpChallengesTable.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(320, 280, 460, 170);

        listChallengeButton.setText("Listar desafios");
        listChallengeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listChallengeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(listChallengeButton);
        listChallengeButton.setBounds(330, 190, 140, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Desafios aceites");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(320, 260, 170, 17);

        rankingButton.setText("Ranking");
        rankingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rankingButtonActionPerformed(evt);
            }
        });
        getContentPane().add(rankingButton);
        rankingButton.setBounds(80, 510, 100, 30);

        deleteButton.setText("Apagar desafio");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        getContentPane().add(deleteButton);
        deleteButton.setBounds(320, 460, 140, 30);

        acpChallengeButton.setText("Aceitar desafio");
        acpChallengeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acpChallengeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(acpChallengeButton);
        acpChallengeButton.setBounds(490, 190, 150, 30);

        background.setIcon(new javax.swing.ImageIcon("C:\\Users\\daniel\\Documents\\GitHub\\QuizClient\\res\\background.jpg")); // NOI18N
        background.setPreferredSize(new java.awt.Dimension(868, 513));
        getContentPane().add(background);
        background.setBounds(0, 0, 880, 560);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Tem a certeza que pretende fazer log out?.", "Saír", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION){
            PDU msg;
            msg = new PDU(this.con.getLabelCount());
            msg.creatLogout();
        
            msg = this.con.getSendAndRecive(msg);
            
            if(msg.isOK()){
                this.dispose();                
                // Ativar butões da frame parent
                this.parentFrame.enableHomeButtons();
            }
        }
    }//GEN-LAST:event_logOutButtonActionPerformed

    /**
     * Mostrar internal frame de ajuda.
     * @param evt 
     */
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        JInternalFrame v = new Help();
        try {
            desktop.add(v);
            v.show();
            v.setClosable(true);
            v.setMaximum(true); // Maximizar a frame interior
        } catch (PropertyVetoException e) {}
    }//GEN-LAST:event_helpButtonActionPerformed

    private void createTextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTextButtonActionPerformed
        String name;
        GregorianCalendar daux = new GregorianCalendar();
        GregorianCalendar date=null;
        
        int h, m, s;
        h = Integer.parseInt(this.hoursTextField.getText().trim());
        m = Integer.parseInt(this.minTextField.getText().trim());
        s = Integer.parseInt(this.secTextField.getText().trim());
        StringBuilder sb = new StringBuilder();
        sb.append(h).append(":").append(m).append(":").append(s);
        
        name=this.nameChallTextField.getText().trim();
        daux = (GregorianCalendar)this.dateChooser.getCalendar();        
        
        date = new GregorianCalendar(daux.get(Calendar.YEAR),daux.get(Calendar.MONTH),daux.get(Calendar.DAY_OF_MONTH),
                h,m,s);
        
        // Linha para adicionar a tabela de desafios aceites
        ArrayList<String> chall = new ArrayList<>();
        chall.add(name);
        chall.add(getStringDate(date));
        chall.add(getStringTime(date));
        chall.add(this.username);
        
        
        // Criar PDU desafio
        PDU msg = new PDU(this.con.getLabelCount());
        msg.creatMakeChallenge(name, date);
        msg = con.getSendAndRecive(msg);
        
        if(!msg.getOK()){
            JOptionPane.showMessageDialog(rootPane, msg.getErro());
        } else {
            JOptionPane.showMessageDialog(rootPane, "Desafio criado com sucesso.");
            this.addChallengeToAcceptedChallengesTable(chall);            
        }
        
    }//GEN-LAST:event_createTextButtonActionPerformed

    /**
     * Preencher tabela com desafios disponíveis já existentes.
     */
    private void fillChallengesTable() {
        DefaultTableModel model = (DefaultTableModel) this.challengesTable.getModel();
        //PRENCHER
    }
    
    /**
     * Preencher tabela com desafios aceites já existentes.
     */
    private void fillAcpChallengesTable() {
        DefaultTableModel model = (DefaultTableModel) this.acpChallengesTable.getModel();
        //PREENCHER
    }
    
    /**
     * Apagar um desafio.
     * @param evt 
     */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int l = this.acpChallengesTable.getSelectedRow();
        String userC = (String)this.acpChallengesTable.getValueAt(l,3);
        String challengeName = (String)this.acpChallengesTable.getValueAt(l, 0);
        
        System.out.println(userC); System.out.println(challengeName);
        
        
        if(!userC.equals(this.username)){
            JOptionPane.showMessageDialog(rootPane, "Apenas o criador do desafio o pode remover.");
        } else {
            int option = JOptionPane.showConfirmDialog(null, "Tem a certeza que pretende apagar o desafio "+challengeName+"?.", "Saír", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION){
                PDU msg = new PDU(this.con.getLabelCount());
                msg.creatdeleteChallenge(challengeName);
                msg = con.getSendAndRecive(msg);

                if(msg.isOK()){
                    DefaultTableModel model = (DefaultTableModel) this.acpChallengesTable.getModel();
                    model.removeRow(l);
                    JOptionPane.showMessageDialog(rootPane, "Desafio "+challengeName+" removido com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(rootPane, msg.getErro());
                }
            }
        }        
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void listChallengeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listChallengeButtonActionPerformed
        PDU msg = new PDU(this.con.getLabelCount());
        msg.creatListChallenge();
        msg = con.getSendAndRecive(msg);
        
        if(msg.isOK()){
            this.addChallengeToAvailableChallengesTable(msg);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Pedimos desculpa, ocorreu um erro.");
        }
        
    }//GEN-LAST:event_listChallengeButtonActionPerformed

    private void hoursTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoursTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hoursTextFieldActionPerformed

    private void acpChallengeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acpChallengeButtonActionPerformed
        int i = this.challengesTable.getSelectedRow();
        ArrayList<String> challenge = new ArrayList<>();
        
        String name = ((String)this.challengesTable.getValueAt(i, 0));
        challenge.add(name);
        challenge.add((String)this.challengesTable.getValueAt(i, 1));
        challenge.add((String)this.challengesTable.getValueAt(i, 2));
        challenge.add((String)this.challengesTable.getValueAt(i, 3));  
        addChallengeToAcceptedChallengesTable(challenge);
        
        // Apagar o desafio da lista de desafios disponíveis
        ((DefaultTableModel)this.challengesTable.getModel()).removeRow(i);
    }//GEN-LAST:event_acpChallengeButtonActionPerformed

    private void rankingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rankingButtonActionPerformed
        PDU msg = new PDU(this.con.getLabelCount());
        msg.creatListRanking();
        //ArrayList<PDU> res = new ArrayList<>();
        msg = con.getSendAndRecive(msg);
        
        List<String> users = new ArrayList<String>();
        List<Integer> scores = new ArrayList<Integer>();
        
        /*
        for(PDU pdu : res){
            users.add(pdu.getAlcunha());
            scores.add(pdu.getScore());
        }
        */
        
        users=msg.getListAlcunha();
        scores=msg.getListScore();
        
        if(!users.isEmpty()){
            //JOptionPane.showMessageDialog(rootPane, "Servidor respondeu OK!");
            // Aceder a pacote e imprimir o ranking           
            
            StringBuilder sb = new StringBuilder();
            int n = users.size();
            for(int i=0; i<n; i++){
                sb.append(i+1).append("º - ").append(users.get(i)).append(": ").append(scores.get(i)).append("\n");
            }
            sb.append("\n");
            
            // JMessagePanel para fazer display do ranking
            JOptionPane.showMessageDialog(rootPane, sb.toString());
            
        } else {
            JOptionPane.showMessageDialog(rootPane, msg.getErro());
        }
    }//GEN-LAST:event_rankingButtonActionPerformed

    
    /**
     * Adiciona um novo desafio criado à tabela de desafios.
     * @param c objeto desafio recentemente criado
     */
    private void addChallengeToAvailableChallengesTable(PDU msg) {
        DefaultTableModel model = (DefaultTableModel) this.challengesTable.getModel();
        List<String> challenges = msg.getListDesafio();
        List<GregorianCalendar> datas  = msg.getListGregorianCalendar();
        List<String> creators = msg.getListAlcunha();
        
        for(int i=0; i<challenges.size(); i++){
            GregorianCalendar gc = datas.get(i);
            String data = new String(gc.get(GregorianCalendar.YEAR)+"/"+(gc.get(GregorianCalendar.MONTH)+1)+"/"+gc.get(GregorianCalendar.DAY_OF_MONTH));
            String hora = new String(gc.get(GregorianCalendar.HOUR_OF_DAY)+":"+gc.get(GregorianCalendar.MINUTE)+":"+gc.get(GregorianCalendar.SECOND));
            model.addRow(new Object[]{challenges.get(i), data, hora, creators.get(i)});
        }
        //adicionar
    }
    
    /**
     * Adiciona um novo desafio criado à tabela de desafios aceites.
     * @param c objeto desafio recentemente criado
     */
    private void addChallengeToAcceptedChallengesTable(ArrayList<String> c) {
        DefaultTableModel model = (DefaultTableModel) this.acpChallengesTable.getModel();
        model.addRow(new Object[]{c.get(0), c.get(1), c.get(2), c.get(3)});
    }

    /**
     * Retorna string devidamente formatada com a data do desafio para apresentar na interface.
     * @return String
     */
    public String getStringDate(GregorianCalendar data) {
        return (data.get(Calendar.DAY_OF_MONTH)+"/"+(data.get(Calendar.MONTH) + 1)+"/"+data.get(Calendar.YEAR));
    }
    
    /**
     * Retorna string com a hora do desafio devidademente formatada para apresentar na interface.
     * @return String
     */
    public String getStringTime(GregorianCalendar data) {
        int h = data.get(Calendar.HOUR_OF_DAY);
        int m = data.get(Calendar.MINUTE);
        int s = data.get(Calendar.SECOND);
        
        String hour, min, seg;
        
        if(h<10){
            hour = "0"+String.valueOf(h);
        } else {
            hour = String.valueOf(h);
        }
        
        if(m<10){
            min = "0"+String.valueOf(m);
        } else {
            min = String.valueOf(m);
        }
        
        if(s<10){
            seg = "0"+String.valueOf(s);
        } else {
            seg = String.valueOf(s);
        }
        
        return(hour+":"+min+":"+seg);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acpChallengeButton;
    private javax.swing.JTable acpChallengesTable;
    private javax.swing.JLabel background;
    private javax.swing.JTable challengesTable;
    private javax.swing.JButton createTextButton;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JTextField hoursTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listChallengeButton;
    private javax.swing.JButton logOutButton;
    private javax.swing.JTextField minTextField;
    private javax.swing.JTextField nameChallTextField;
    private javax.swing.JButton rankingButton;
    private javax.swing.JTextField secTextField;
    // End of variables declaration//GEN-END:variables
}