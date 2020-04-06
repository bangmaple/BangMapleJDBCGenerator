/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bangmaple.jdbcgenerator;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import javax.swing.JFileChooser;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author bangmaple
 */
public class MainController extends javax.swing.JFrame {

    private static String SAVE_FILE_PATH = "";
    private static Boolean HAVE_A_PACKAGE_NAME = Boolean.FALSE;
    private static String SYSTEM_SLASH = "/";

    public MainController() {
        initFrame();
        popupMenu();
        loadLogo();
        changeSlash();
    }

    private void initFrame() {
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(null);
        initComponents();
        this.setLocationRelativeTo(null);
        setTableColumnSize();
        chkPublic.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!chkPublic.isSelected()) {
                    JOptionPane.showMessageDialog(null, new StringBuilder("Remember when removing ")
                            .append("\"public\" modifier could make other classes in ")
                            .append("other packages can't access to this.").toString());
                }
            }
        });
        chkFinal.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkFinal.isSelected()) {
                    JOptionPane.showMessageDialog(null, new StringBuilder("Remember when adding ")
                            .append("\"final\" modifier make this class can't be inherited!").toString());
                }
            }
        });
    }

    private void changeSlash() {
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            SYSTEM_SLASH = "\\";
        }
    }

    private void setTableColumnSize() {
        int[] columnsWidth = {90, 85, 200, 75, 70};
        int i = 0;
        for (int columnWidthSize : columnsWidth) {
            TableColumn column = jTable1.getColumnModel().getColumn(i++);
            column.setMinWidth(columnWidthSize);
            column.setMaxWidth(columnWidthSize);
            column.setPreferredWidth(columnWidthSize);
        }
    }

    private void loadLogo() {
        Icon logoIcon = new ImageIcon(new ImageIcon(getClass()
                .getResource("/logo.png")).getImage()
                .getScaledInstance(250, 45, java.awt.Image.SCALE_SMOOTH));
        lblLogo.setIcon(logoIcon);
        Icon logoJDBC = new ImageIcon(new ImageIcon(getClass()
                .getResource("/jdbc.png")).getImage()
                .getScaledInstance(120, 50, java.awt.Image.SCALE_SMOOTH));
        lblLogoJDBC.setIcon(logoJDBC);
        Icon closeIcon = new ImageIcon(new ImageIcon(getClass()
                .getResource("/icons8_multiply_18px_1.png")).getImage()
                .getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH));
        lblClose.setIcon(closeIcon);

        Icon minMaxIcon = new ImageIcon(new ImageIcon(getClass()
                .getResource("/icons8_rectangle_stroked_18px.png")).getImage()
                .getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH));
        lblMaxMin.setIcon(minMaxIcon);

        Icon minIcon = new ImageIcon(new ImageIcon(getClass()
                .getResource("/icons8_minus_18px_1.png")).getImage()
                .getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH));
        lblMin.setIcon(minIcon);

    }

    private void popupMenu() {
        CutCopyPasteActionSupport support = new CutCopyPasteActionSupport();
        support.setPopup(txtProjectName, txtClassNameJDBC,
                txtClassNameJDBCDAO, txtDBName, txtClassName,
                txtClassNameJDBCDAO, txtPassword, txtUsername, txtPackageName,
                txtExtendsDTO, txtImplementsDTO, txtGetConnection);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane1 = new DraggableTabbedPane(this);
        jPanel1 = new MotionPanel(this);
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtClassName = new javax.swing.JTextField();
        chkPublicDTO = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        txtExtendsDTO = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtImplementsDTO = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        chkFinalDTO = new javax.swing.JCheckBox();
        chkSerializableDTO = new javax.swing.JCheckBox();
        btnGenerateEntity = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new MotionPanel(this);
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        txtDBName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        txtClassNameJDBC = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        chkPublic = new javax.swing.JCheckBox();
        chkFinal = new javax.swing.JCheckBox();
        chkSerializable = new javax.swing.JCheckBox();
        btnChooseProject = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtGetConnection = new javax.swing.JTextField();
        cbGetConnection = new javax.swing.JComboBox<>();
        jPanel6 = new MotionPanel(this);
        jPanel12 = new javax.swing.JPanel();
        txtClassNameJDBCDAO = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        chkPublicJDBCDAO = new javax.swing.JCheckBox();
        chkFinalJDBCDAO = new javax.swing.JCheckBox();
        btnGenerateJDBCDAO = new javax.swing.JButton();
        chkImplSerializable = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        chkConnection = new javax.swing.JCheckBox();
        chkPreparedStatement = new javax.swing.JCheckBox();
        chkResultSet = new javax.swing.JCheckBox();
        chkGetterConnectionJDBCDAO = new javax.swing.JCheckBox();
        chkGetterPreparedStatementJDBCDAO = new javax.swing.JCheckBox();
        chkSetterPreparedStatementJDBCDAO = new javax.swing.JCheckBox();
        chkSetterConnectionJDBCDAO = new javax.swing.JCheckBox();
        cbPreparedStatementModifier = new javax.swing.JComboBox<>();
        cbConnectionModifier = new javax.swing.JComboBox<>();
        cbResultSetModifier = new javax.swing.JComboBox<>();
        chkGetterResultSetJDBCDAO = new javax.swing.JCheckBox();
        chkSetterResultSetJDBCDAO = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        chkAddJDBCDAO = new javax.swing.JCheckBox();
        chkRemoveJDBCDAO = new javax.swing.JCheckBox();
        chkFindJDBCDAO = new javax.swing.JCheckBox();
        chkUpdateJDBCDAO = new javax.swing.JCheckBox();
        chkgetAllJDBCDAO = new javax.swing.JCheckBox();
        chkCloseConnection = new javax.swing.JCheckBox();
        cbAddModifier = new javax.swing.JComboBox<>();
        cbGetAllModifier = new javax.swing.JComboBox<>();
        cbUpdateModifier = new javax.swing.JComboBox<>();
        cbFindModifier = new javax.swing.JComboBox<>();
        cbRemoveModifier = new javax.swing.JComboBox<>();
        cbCloseConnectionModifier = new javax.swing.JComboBox<>();
        lblMaxMin = new javax.swing.JLabel();
        lblMin = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblLogoJDBC = new javax.swing.JLabel();
        txtProjectName = new javax.swing.JTextField();
        txtPackageName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel11.setText("Class attribute(s)");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Class generation"));

        jLabel10.setText("Class name:");

        txtClassName.setText("GenericDTO");

        chkPublicDTO.setSelected(true);
        chkPublicDTO.setText("public");

        jLabel15.setText("extends");

        jLabel16.setText("implements (separating by ,)");

        chkFinalDTO.setText("final");

        chkSerializableDTO.setText("implements java.io.Serializable");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtClassName, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chkSerializableDTO)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkFinalDTO)
                    .addComponent(chkPublicDTO))
                .addGap(19, 19, 19))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtExtendsDTO, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                    .addComponent(txtImplementsDTO))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPublicDTO))
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkSerializableDTO)
                    .addComponent(chkFinalDTO))
                .addGap(3, 3, 3)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtExtendsDTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImplementsDTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(15, 15, 15))
        );

        btnGenerateEntity.setText("Select project path and generate...");
        btnGenerateEntity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateEntityActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"private", "String", "id",  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", "String", "name",  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)},
                {"", null, null,  new Boolean(true),  new Boolean(true),  new Boolean(false)}
            },
            new String [] {
                "Modifier", "Type", "Name", "Getter", "Setter", "Constructor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jLabel18.setText("Input the properties into the cells before generating if you want");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(btnGenerateEntity, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGenerateEntity, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Class (DTO)", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SQL Connection"));

        jLabel3.setText("IP Address:");

        txtIP.setText("127.0.0.1");

        jLabel4.setText("Port:");

        txtPort.setText("1433");

        jLabel5.setText("Username:");

        txtUsername.setText("sa");

        jLabel6.setText("Password:");

        jLabel7.setText("Database name:");

        jLabel8.setText("(Leave blank for 'master')");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(18, 18, 18))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(52, 52, 52)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(48, 48, 48)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtDBName))
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPort, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(txtPassword))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDBName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Class generation"));
        jPanel13.setPreferredSize(new java.awt.Dimension(400, 148));

        txtClassNameJDBC.setText("DBUtils");

        jLabel1.setText("Class name:");

        chkPublic.setSelected(true);
        chkPublic.setText("public");

        chkFinal.setText("final");

        chkSerializable.setText("implements Serializable");

        btnChooseProject.setText("Select project path and generate...");
        btnChooseProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseProjectActionPerformed(evt);
            }
        });

        btnConnect.setText("Check connection only");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtClassNameJDBC, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(chkPublic)
                                .addGap(18, 18, 18)
                                .addComponent(chkFinal)
                                .addGap(18, 18, 18)
                                .addComponent(chkSerializable)
                                .addGap(49, 49, 49)
                                .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(btnChooseProject, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtClassNameJDBC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPublic)
                    .addComponent(chkSerializable)
                    .addComponent(chkFinal)
                    .addComponent(btnConnect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChooseProject)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection - Method generation"));

        jLabel9.setText("Method name:");

        jLabel14.setText("Method type:");

        txtGetConnection.setText("getConnection");

        cbGetConnection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "public", "<default>", "protected", "private" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbGetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtGetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbGetConnection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("JDBC Utils", jPanel2);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Class generation"));

        txtClassNameJDBCDAO.setText("GenericDAO");

        jLabel12.setText("Class name:");

        chkPublicJDBCDAO.setSelected(true);
        chkPublicJDBCDAO.setText("public");

        chkFinalJDBCDAO.setText("final");

        btnGenerateJDBCDAO.setText("Select project path and generate...");
        btnGenerateJDBCDAO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateJDBCDAOActionPerformed(evt);
            }
        });

        chkImplSerializable.setText("impl. Serializable");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(29, 29, 29)
                        .addComponent(txtClassNameJDBCDAO))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(chkPublicJDBCDAO)
                        .addGap(18, 18, 18)
                        .addComponent(chkFinalJDBCDAO)
                        .addGap(18, 18, 18)
                        .addComponent(chkImplSerializable)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerateJDBCDAO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClassNameJDBCDAO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPublicJDBCDAO)
                    .addComponent(chkFinalJDBCDAO)
                    .addComponent(btnGenerateJDBCDAO)
                    .addComponent(chkImplSerializable))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));

        chkConnection.setSelected(true);
        chkConnection.setText("Connection");
        chkConnection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkConnectionItemStateChanged(evt);
            }
        });

        chkPreparedStatement.setSelected(true);
        chkPreparedStatement.setText("PreparedStatement");
        chkPreparedStatement.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPreparedStatementItemStateChanged(evt);
            }
        });

        chkResultSet.setSelected(true);
        chkResultSet.setText("ResultSet");
        chkResultSet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkResultSetItemStateChanged(evt);
            }
        });

        chkGetterConnectionJDBCDAO.setText("Getter");

        chkGetterPreparedStatementJDBCDAO.setText("Getter");

        chkSetterPreparedStatementJDBCDAO.setText("Setter");

        chkSetterConnectionJDBCDAO.setText("Setter");

        cbPreparedStatementModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "private", "<default>", "public", "protected" }));

        cbConnectionModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "private", "<default>", "public", "protected" }));

        cbResultSetModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "private", "<default>", "public", "protected" }));

        chkGetterResultSetJDBCDAO.setText("Getter");

        chkSetterResultSetJDBCDAO.setText("Setter");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator6)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(chkPreparedStatement)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbPreparedStatementModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkConnection)
                                    .addComponent(chkResultSet))
                                .addGap(87, 87, 87)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbConnectionModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbResultSetModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(chkGetterConnectionJDBCDAO)
                                .addGap(18, 18, 18)
                                .addComponent(chkSetterConnectionJDBCDAO, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(chkGetterPreparedStatementJDBCDAO)
                                .addGap(18, 18, 18)
                                .addComponent(chkSetterPreparedStatementJDBCDAO, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(chkGetterResultSetJDBCDAO)
                                .addGap(18, 18, 18)
                                .addComponent(chkSetterResultSetJDBCDAO, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(chkGetterConnectionJDBCDAO, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkSetterConnectionJDBCDAO))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chkConnection)
                        .addComponent(cbConnectionModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPreparedStatement)
                    .addComponent(cbPreparedStatementModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkGetterPreparedStatementJDBCDAO)
                    .addComponent(chkSetterPreparedStatementJDBCDAO))
                .addGap(10, 10, 10)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbResultSetModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkGetterResultSetJDBCDAO)
                    .addComponent(chkSetterResultSetJDBCDAO)
                    .addComponent(chkResultSet))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Generate method"));

        chkAddJDBCDAO.setText("add");
        chkAddJDBCDAO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkAddJDBCDAOItemStateChanged(evt);
            }
        });

        chkRemoveJDBCDAO.setText("remove");
        chkRemoveJDBCDAO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkRemoveJDBCDAOItemStateChanged(evt);
            }
        });

        chkFindJDBCDAO.setText("find");
        chkFindJDBCDAO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkFindJDBCDAOItemStateChanged(evt);
            }
        });

        chkUpdateJDBCDAO.setText("update");
        chkUpdateJDBCDAO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkUpdateJDBCDAOItemStateChanged(evt);
            }
        });

        chkgetAllJDBCDAO.setText("getAll");
        chkgetAllJDBCDAO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkgetAllJDBCDAOItemStateChanged(evt);
            }
        });

        chkCloseConnection.setSelected(true);
        chkCloseConnection.setText("closeConnection");
        chkCloseConnection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCloseConnectionItemStateChanged(evt);
            }
        });

        cbAddModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "public", "<default>", "protected", "private" }));
        cbAddModifier.setEnabled(false);

        cbGetAllModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "public", "<default>", "protected", "private" }));
        cbGetAllModifier.setEnabled(false);

        cbUpdateModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "public", "<default>", "protected", "private" }));
        cbUpdateModifier.setEnabled(false);

        cbFindModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "public", "<default>", "protected", "private" }));
        cbFindModifier.setEnabled(false);

        cbRemoveModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "public", "<default>", "protected", "private" }));
        cbRemoveModifier.setEnabled(false);

        cbCloseConnectionModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "private", "<default>", "public", "protected" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(chkCloseConnection)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbCloseConnectionModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(chkAddJDBCDAO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbAddModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(chkFindJDBCDAO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbFindModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chkgetAllJDBCDAO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkRemoveJDBCDAO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(cbRemoveModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(chkUpdateJDBCDAO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbUpdateModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbGetAllModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkAddJDBCDAO)
                    .addComponent(cbAddModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkRemoveJDBCDAO)
                    .addComponent(chkUpdateJDBCDAO)
                    .addComponent(cbUpdateModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbRemoveModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkFindJDBCDAO)
                    .addComponent(chkgetAllJDBCDAO)
                    .addComponent(cbFindModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbGetAllModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkCloseConnection)
                    .addComponent(cbCloseConnectionModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("JDBC DAO", jPanel6);

        lblMaxMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMaxMinMouseClicked(evt);
            }
        });

        lblMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinMouseClicked(evt);
            }
        });

        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });

        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
        });

        lblLogoJDBC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoJDBCMouseClicked(evt);
            }
        });

        txtProjectName.setEditable(false);
        txtProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProjectNameActionPerformed(evt);
            }
        });

        jLabel2.setText("Package name:");

        jLabel13.setText("Project path:");

        jLabel17.setText("<html>fb.com/BangMaple<br/>github.com/BangMaple</html>");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLogoJDBC, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPackageName, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                                    .addComponent(txtProjectName))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 0, 0)
                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addComponent(lblLogoJDBC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPackageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean checkDBConnection() {
        Connection conn = null;
        boolean check = false;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(new StringBuilder("jdbc:sqlserver://")
                    .append(txtIP.getText()).append(":").append(txtPort.getText())
                    .append(";databaseName=").append(txtDBName.getText())
                    .append(";username=").append(txtUsername.getText())
                    .append(";password=").append(txtPassword.getText()).toString());
            if (conn != null) {
                check = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            check = false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    check = false;
                }
            }
        }
        return check;
    }

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        // TODO add your handling code here:
        if (validateConnection()) {
            if (checkDBConnection()) {
                txtInfo.setText("(+) Successfully connected to the given database name.");
            } else {
                txtInfo.setText("(-) Failed to connect to the given database name.");
                JOptionPane.showMessageDialog(this, new StringBuilder("Trying to ")
                        .append("connect to database failed!\n")
                        .append("Please try to leave the database name blank to ")
                        .append("use the default database or check again\n")
                        .append("the IP Address and the port. Even the username ")
                        .append("and password must be correct.\n")
                        .append("If this happen again, please check if you already ")
                        .append("opened the IP forwarding in SQL Server ")
                        .append("Configuration Wizard.").toString(),
                        new StringBuilder("Error while connecting ")
                                .append("to database").toString(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnConnectActionPerformed
    private class MyFileChooser extends JFileChooser {

        public MyFileChooser(String path) {
            super(path);
        }

        @Override
        public JDialog createDialog(Component parent) throws HeadlessException {
            return super.createDialog(parent);
        }

    }

    private void generateJDBCUtils() {
        String publicModifier = "";
        String implementsSerializable = "";
        String serializable = "";
        String finalModifier = "";
        String getConnectionModifier = cbGetConnection.getModel().getSelectedItem() + " ";
        if (getConnectionModifier.equals("<default> ")) {
            getConnectionModifier = "";
        }
        if (chkPublic.isSelected()) {
            publicModifier = "public ";
        }
        if (chkSerializable.isSelected()) {
            implementsSerializable = " implements Serializable";
            serializable = "import java.io.Serializable;";
        }
        if (chkFinal.isSelected()) {
            finalModifier = "final ";
        }
        BufferedWriter bw = null;
        String packagePathName = "";
        if (!txtPackageName.getText().trim().isEmpty()) {
            packagePathName = txtPackageName.getText().trim().replace(".", SYSTEM_SLASH);
        }
        File path = new File(new StringBuilder(SAVE_FILE_PATH).append(packagePathName).toString());
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        try {
            bw = new BufferedWriter(new PrintWriter(
                    new StringBuilder(SAVE_FILE_PATH).append(packagePathName)
                            .append(SYSTEM_SLASH).append(txtClassNameJDBC.getText()
                            .trim()).append(".java").toString()));
            bw.newLine();
            bw.write("/**");
            bw.newLine();
            bw.write(" * This class was generated by BangMaple Class Generator");
            bw.newLine();
            bw.write(new StringBuilder(" * Date: ").append(
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))
                    .toString());
            bw.newLine();
            bw.write(" */");
            if (HAVE_A_PACKAGE_NAME) {
                bw.newLine();
                bw.write(new StringBuilder("package ")
                        .append(txtPackageName.getText().trim()).append(";").toString());
                bw.newLine();
            }
            bw.newLine();
            bw.write("import java.sql.SQLException;");
            bw.newLine();
            bw.write("import java.sql.Connection;");
            bw.newLine();
            bw.write("import java.sql.DriverManager;");
            bw.newLine();
            if (!serializable.equals("")) {
                bw.write(serializable);
                bw.newLine();
            }
            bw.newLine();
            bw.write(new StringBuilder(publicModifier).append(finalModifier)
                    .append("class ").append(txtClassNameJDBC.getText())
                    .append(implementsSerializable).append(" {").toString());
            bw.newLine();
            bw.newLine();
            bw.write(new StringBuilder("    ").append(getConnectionModifier)
                    .append("static ").append("Connection ").append(txtGetConnection.getText())
                    .append("() {").toString());
            bw.newLine();
            bw.newLine();
            bw.write("\tConnection conn = null;");
            bw.newLine();
            bw.write("\ttry {");
            bw.newLine();
            bw.write("\t    Class.forName(\"com.microsoft.sqlserver.jdbc.SQLServerDriver\");");
            bw.newLine();
            bw.write("\t    conn = DriverManager.getConnection(\"jdbc:sqlserver://");
            bw.write(new StringBuilder(txtIP.getText()).append(":")
                    .append(txtPort.getText()).append(";databaseName=")
                    .append(txtDBName.getText()).append(";username=")
                    .append(txtUsername.getText()).append(";password=")
                    .append(txtPassword.getText()).append("\");").toString());
            bw.newLine();
            bw.write("\t} catch (SQLException | ClassNotFoundException e) {");
            bw.newLine();
            bw.write(new StringBuilder("\t    System.out.println(\"Error at ")
                    .append(txtClassNameJDBC.getText())
                    .append(": \" + e.getMessage());").toString());
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.write("\treturn conn;");
            bw.newLine();
            bw.write("    }");
            bw.newLine();
            bw.write("}");
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Successfully generated this java class file.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while generating class file.", "Error while generating class", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while generating class file.", "Error while generating class", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getDataFromDTOTable(final List<String> varList,
            final List<String> attributeList, final List<String> getterList,
            final List<String> setterList, final List<String> constructorList) {
        DefaultTableModel tblModel = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < tblModel.getRowCount(); i++) {
            if (tblModel.getValueAt(i, 2) == null) {
                break;
            }
            String var = "";
            if (!String.valueOf(tblModel.getValueAt(i, 0)).trim().isEmpty()) {
                var = new StringBuilder(var).append(tblModel.getValueAt(i, 0)).append(" ").toString();
            }
            var = new StringBuilder(var).append(tblModel.getValueAt(i, 1)).toString();
            var = new StringBuilder(var).append(" ").append(tblModel.getValueAt(i, 2)).toString();
            var = new StringBuilder(var).append(" ").append(tblModel.getValueAt(i, 3)).toString();
            var = new StringBuilder(var).append(" ").append(tblModel.getValueAt(i, 4)).toString();
            var = new StringBuilder(var).append(" ").append(tblModel.getValueAt(i, 5)).toString();
            varList.add(var);
        }
        for (int i = 0; i < varList.size(); i++) {
            StringTokenizer stk = new StringTokenizer(varList.get(i), " ");
            while (stk.hasMoreTokens()) {
                String getter = "";
                String setter = "";
                String var = "";
                if (stk.countTokens() == 6) {
                    var = new StringBuilder(var).append(stk.nextToken())
                            .append(" ").toString();
                }
                String type = stk.nextToken();
                String name = stk.nextToken();
                var = new StringBuilder(var).append(type).append(" ")
                        .append(name).append(";").toString();
                if (stk.nextToken().equals("true")) {
                    getter = new StringBuilder(getter).append("\n    public ")
                            .append(type).append(" get")
                            .append(name.substring(0, 1).toUpperCase())
                            .append(name.substring(1)).append("() {\n")
                            .append("\treturn ").append(name).append(";\n")
                            .append("    }\n").toString();
                }
                if (stk.nextToken().equals("true")) {
                    setter = new StringBuilder(setter)
                            .append("\n    public void set")
                            .append(name.substring(0, 1).toUpperCase())
                            .append(name.substring(1)).append("(")
                            .append(type).append(" ").append(name).append(") {\n")
                            .append("\tthis.").append(name).append(" = ")
                            .append(name).append(";\n")
                            .append("    }\n").toString();
                }
                attributeList.add(var);
                getterList.add(getter);
                setterList.add(setter);
                if (stk.nextToken().equals("true")) {
                    constructorList.add(new StringBuilder(type).append(" ").append(name).toString());
                }
            }
        }
    }

    private String getGetterDTO(final List<String> getterList) {
        String getter = "";
        if (getterList.size() > 0) {
            for (int i = 0; i < getterList.size(); i++) {
                getter = new StringBuilder(getter).append(getterList.get(i)).toString();
            }
        }
        return getter;
    }

    private String getSetterDTO(final List<String> setterList) {
        String setter = "";
        if (setterList.size() > 0) {
            for (int i = 0; i < setterList.size(); i++) {
                setter = new StringBuilder(setter).append(setterList.get(i)).toString();
            }
        }
        return setter;
    }

    private String getAttributesDTO(final List<String> attributeList) {
        String var = "";
        for (int i = 0; i < attributeList.size(); i++) {
            var = new StringBuilder(var).append("    ")
                    .append(attributeList.get(i)).append("\n").toString();
        }
        return var;
    }

    private String getConstructorDTO(final List<String> constructorList) {
        String constructor = new StringBuilder("    public ")
                .append(txtClassName.getText()).append("(").toString();
        if (constructorList.size() > 0) {
            for (int i = 0; i < constructorList.size(); i++) {
                if (i != constructorList.size() - 1) {
                    constructor = new StringBuilder(constructor)
                            .append(constructorList.get(i)).append(", ").toString();
                } else {
                    constructor = new StringBuilder(constructor)
                            .append(constructorList.get(i)).toString();
                }
            }
            constructor = new StringBuilder(constructor).append(") {\n").toString();
            for (int i = 0; i < constructorList.size(); i++) {
                String testVar = constructorList.get(i)
                        .substring(constructorList.get(i).lastIndexOf(" ") + 1,
                                constructorList.get(i).length());
                constructor = new StringBuilder(constructor).append("\tthis.")
                        .append(testVar).append(" = ").append(testVar)
                        .append(";\n").toString();
            }
            constructor = new StringBuilder(constructor).append("    }\n\n")
                    .append("    public ").append(txtClassName.getText())
                    .append("() {\n").append("    }").toString();

        } else {
            constructor = new StringBuilder(constructor).append(") {\n")
                    .append("    }").toString();
        }
        return constructor;
    }

    private void generateDTO() {
        List<String> varList = new ArrayList<>();
        List<String> attributeList = new ArrayList<>();
        List<String> getterList = new ArrayList<>();
        List<String> setterList = new ArrayList<>();
        List<String> constructorList = new ArrayList<>();

        getDataFromDTOTable(varList, attributeList, getterList, setterList, constructorList);
        String var = getAttributesDTO(attributeList);
        String getter = getGetterDTO(getterList);
        String setter = getSetterDTO(setterList);
        String constructor = getConstructorDTO(constructorList);

        String publicModifier = "";
        String implementsSerializable = "";
        String serializable = "";
        String finalModifier = "";
        String extendClass = "";

        if (!txtExtendsDTO.getText().isEmpty()) {
            extendClass = " extends " + txtExtendsDTO.getText().trim();
        }

        if (chkPublicDTO.isSelected()) {
            publicModifier = "public ";
        }
        if (chkSerializableDTO.isSelected()) {
            implementsSerializable = " implements Serializable";
            serializable = "import java.io.Serializable;";
        }
        if (!txtImplementsDTO.getText().trim().isEmpty()) {
            if (implementsSerializable.isEmpty()) {
                implementsSerializable = " implements";
            }
            if (txtImplementsDTO.getText().contains(",")) {

                String[] split = txtImplementsDTO.getText().trim().split(",");
                for (int i = 0; i < split.length; i++) {
                    if (i == 0 && serializable.isEmpty()) {
                        implementsSerializable = new StringBuilder(implementsSerializable).append(" ")
                                .append(split[i].trim()).toString();
                    } else {
                        implementsSerializable = new StringBuilder(implementsSerializable)
                                .append(", ").append(split[i].trim()).toString();
                    }
                }
            } else {
                if (!serializable.isEmpty()) {
                    implementsSerializable += ", " + txtImplementsDTO.getText().trim();
                } else {
                    implementsSerializable += txtImplementsDTO.getText().trim();
                }
            }
        }
        if (chkFinalDTO.isSelected()) {
            finalModifier = "final ";
        }
        BufferedWriter bw = null;
        String packagePathName = "";
        if (!txtPackageName.getText().trim().isEmpty()) {
            packagePathName = txtPackageName.getText().trim().replace(".", SYSTEM_SLASH);
        }
        File path = new File(new StringBuilder(SAVE_FILE_PATH)
                .append(packagePathName).toString());
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        try {
            bw = new BufferedWriter(new PrintWriter(new StringBuilder(SAVE_FILE_PATH)
                    .append(packagePathName).append(SYSTEM_SLASH)
                    .append(txtClassName.getText().trim()).append(".java").toString()));
            bw.newLine();
            bw.write("/**");
            bw.newLine();
            bw.write(" * This class was generated by BangMaple Class Generator");
            bw.newLine();
            bw.write(new StringBuilder(" * Date: ").append(new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm:ss").format(new Date())).toString());
            bw.newLine();
            bw.write(" */");
            if (HAVE_A_PACKAGE_NAME) {
                bw.newLine();
                bw.write(new StringBuilder("package ")
                        .append(txtPackageName.getText().trim()).append(";").toString());
                bw.newLine();
            }
            bw.newLine();
            if (!implementsSerializable.isEmpty()) {
                bw.write(serializable);
                bw.newLine();
            }
            bw.newLine();
            bw.write(new StringBuilder(publicModifier).append(finalModifier)
                    .append("class ").append(txtClassName.getText()).append(extendClass)
                    .append(implementsSerializable).append(" {").toString());
            bw.newLine();
            bw.write(new StringBuilder("\n").append(var).toString());
            bw.newLine();
            bw.write(constructor);
            bw.newLine();
            bw.write(getter);
            bw.newLine();
            bw.write(setter);
            bw.newLine();
            bw.write("}");
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Successfully generated this java class file.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while generating class file.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while generating class file.",
                        "Error while generating class", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void chooseSaveDialogStrategy(final MyFileChooser chooser, final String type) {
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        switch (type) {
            case "jdbcUtils":
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassNameJDBC.getText()).append(".java").toString()));
                break;
            case "jdbcDAO":
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassNameJDBCDAO.getText()).append(".java").toString()));
                break;
            default:
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassName.getText()).append(".java").toString()));
                break;
        }
        chooser.setFileFilter(new FileNameExtensionFilter("Only accept NetBeans project folder!",
                "java"));

    }

    private void initSaveDialog(String type) {
        MyFileChooser chooser = new MyFileChooser(".");
        chooseSaveDialogStrategy(chooser, type);
        final JDialog dialog = chooser.createDialog(null);
        chooser.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFileChooser chooser = (JFileChooser) evt.getSource();
                if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
                    String filename = chooser.getSelectedFile().getAbsolutePath();
                    if (!filename.endsWith(".java")) {
                        filename = new StringBuilder(filename).append(".java").toString();
                    }
                    SAVE_FILE_PATH = new StringBuilder(filename.substring(0,
                            filename.lastIndexOf(SYSTEM_SLASH)))
                            .append(SYSTEM_SLASH).toString();
                    String projectPath = new StringBuilder(filename.substring(0,
                            filename.lastIndexOf(SYSTEM_SLASH)))
                            .append(SYSTEM_SLASH).toString();
                    txtProjectName.setText(projectPath);

                    dialog.setVisible(false);
                } else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
                    dialog.setVisible(false);
                }
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });

        dialog.setVisible(true);
    }

    private boolean validateJDBCUtilsData() {
        if (txtClassNameJDBC.getText().matches("^[\\_a-zA-Z0-9]{1,255}$")) {
            if (txtClassNameJDBC.getText().substring(0, 1).matches("^[0-9]$")
                    || txtClassNameJDBC.getText().substring(0, 1).matches("^[\\.]$")) {
                JOptionPane.showMessageDialog(this, new StringBuilder("Can't generate due to class name is not valid!\n")
                        .append("The first letter must not a number.").toString());
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Can't generate due to class name is not valid!");
            return false;
        }
        if (txtGetConnection.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "getConnection method name musn't be blank!");
            return false;
        } else {
            if (!txtGetConnection.getText().trim().matches("^[a-zA-Z0-9]{1,}$")
                    || String.valueOf(txtGetConnection.getText().charAt(0)).matches("^[0-9]$")) {
                JOptionPane.showMessageDialog(this, "Please follow the correct method name format.");
                return false;
            }
        }
        return true;
    }

    private void btnChooseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseProjectActionPerformed
        if (!validateConnection()) {
            return;
        }
        if (!validateJDBCUtilsData()) {
            return;
        }
        selectProjectPath("jdbcUtils");
    }//GEN-LAST:event_btnChooseProjectActionPerformed

    private void setSaveFilePath(boolean check, String type) {
        if (!check) {
            initSaveDialog(type);
            String projectPath;
            try {
                projectPath = new StringBuilder(SAVE_FILE_PATH
                        .substring(0, SAVE_FILE_PATH.lastIndexOf(SYSTEM_SLASH)))
                        .append(SYSTEM_SLASH).toString();
            } catch (StringIndexOutOfBoundsException e) {
                return;
            }
            File f1 = new File(new StringBuilder(projectPath).append("build.xml")
                    .toString());
            File f2 = new File(new StringBuilder(projectPath).append("pom.xml")
                    .toString());
            if (f1.isFile() || f2.isFile()) {
                if (f2.isFile()) {
                    SAVE_FILE_PATH = new StringBuilder(SAVE_FILE_PATH)
                            .append("src").append(SYSTEM_SLASH).append("main")
                            .append(SYSTEM_SLASH).append("java")
                            .append(SYSTEM_SLASH).toString();
                } else {
                    SAVE_FILE_PATH = new StringBuilder(SAVE_FILE_PATH)
                            .append("src").append(SYSTEM_SLASH).toString();
                }
                switch (type) {
                    case "jdbcUtils":
                        generateJDBCUtils();
                        break;
                    case "jdbcDAO":
                        System.out.println("dao");
                        generateJDBCDAO();
                        break;
                    case "dto":
                        generateDTO();
                        break;
                    default:
                        break;
                }
            } else {
                txtProjectName.setText("");
                JOptionPane.showMessageDialog(this,
                        new StringBuilder("Only accept NetBeansProjects's project folder.")
                                .append("\nJava with Ant or Maven is acceptable!").toString());
            }
        } else {
            SAVE_FILE_PATH = new StringBuilder(SAVE_FILE_PATH.substring(0,
                    SAVE_FILE_PATH.lastIndexOf(SYSTEM_SLASH)))
                    .append(SYSTEM_SLASH).toString();
            switch (type) {
                case "jdbcUtils":
                    generateJDBCUtils();
                    break;
                case "jdbcDAO":
                    generateJDBCDAO();
                    break;
                default:
                    generateDTO();
                    break;
            }
        }
    }

    private void selectProjectPath(String type) {
        boolean check = false;
        if (txtPackageName.getText().trim().isEmpty()) {
            HAVE_A_PACKAGE_NAME = false;
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you don't want a package name?");
            if (choice != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this,
                        "Please enter the corresponding package name then\ntry generating again!");
                return;
            }
        } else {
            HAVE_A_PACKAGE_NAME = true;
        }

        if (!txtProjectName.getText().trim().isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to change another project path?");
            if (choice != JOptionPane.OK_OPTION) {
                check = true;
            }
        }
        System.out.println(check);
        setSaveFilePath(check, type);
    }

    private boolean validateJDBCDAOClassName() {
        if (txtClassNameJDBCDAO.getText().matches("^[\\_a-zA-Z0-9]{1,255}$")) {
            if (txtClassNameJDBCDAO.getText().substring(0, 1).matches("^[0-9]$")
                    || txtClassNameJDBCDAO.getText().substring(0, 1).matches("^[\\.]$")) {
                JOptionPane.showMessageDialog(this, new StringBuilder("Can't generate due to class name is not valid!\n")
                        .append("The first letter must not a number.").toString());
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Can't generate due to class name is not valid!");
            return false;
        }
        return true;
    }

    private void generateJDBCDAO() {
        String closeConnection = "";
        String importConnection = "";
        String importPreparedStatement = "";
        String importResultSet = "";
        String importSQLException = "";
        String finalModifier = "";
        String getter = "";
        String setter = "";
        String addMethod = "";
        String updateMethod = "";
        String deleteMethod = "";
        String findMethod = "";
        String getAllMethod = "";
        String importSerializable = "";
        String serializableModifier = "";
        String importListAndLinkedList = "";

        String connectionModifier = cbConnectionModifier.getModel().getSelectedItem() + " ";
        String preparedStatementModifier = cbPreparedStatementModifier.getModel().getSelectedItem() + " ";
        String resultSetModifier = cbResultSetModifier.getModel().getSelectedItem() + " ";
        String addModifier = cbAddModifier.getModel().getSelectedItem() + " ";
        String findModifier = cbFindModifier.getModel().getSelectedItem() + " ";
        String removeModifier = cbRemoveModifier.getModel().getSelectedItem() + " ";
        String getAllModifier = cbGetAllModifier.getModel().getSelectedItem() + " ";
        String updateModifier = cbUpdateModifier.getModel().getSelectedItem() + " ";
        String closeConnectionModifier = cbCloseConnectionModifier.getModel().getSelectedItem() + " ";

        if (!validateJDBCDAOClassName()) {
            return;
        }
        if (chkImplSerializable.isSelected()) {
            importSerializable = "import java.io.Serializable;";
            serializableModifier = "implements Serializable ";
        }
        if (chkFinalJDBCDAO.isSelected()) {
            finalModifier = " final";
        }
        if (connectionModifier.equals("<default> ")) {
            connectionModifier = "";
        }
        if (preparedStatementModifier.equals("<default> ")) {
            preparedStatementModifier = "";
        }
        if (resultSetModifier.equals("<default> ")) {
            resultSetModifier = "";
        }
        if (addModifier.equals("<default> ")) {
            addModifier = "";
        }
        if (removeModifier.equals("<default> ")) {
            removeModifier = "";
        }
        if (updateModifier.equals("<default> ")) {
            updateModifier = "";
        }
        if (findModifier.equals("<default> ")) {
            findModifier = "";
        }
        if (getAllModifier.equals("<default> ")) {
            getAllModifier = "";
        }
        if (closeConnectionModifier.equals("<default> ")) {
            closeConnectionModifier = "";
        }

        if (chkConnection.isSelected()) {
            importConnection = "import java.sql.Connection;";
        }
        if (chkPreparedStatement.isSelected()) {
            importPreparedStatement = "import java.sql.PreparedStatement;";
        }
        if (chkResultSet.isSelected()) {
            importResultSet = "import java.sql.ResultSet;";
        }

        if (chkGetterConnectionJDBCDAO.isSelected()) {
            getter = new StringBuilder(getter)
                    .append("\n    public Connection getConnection() {\n")
                    .append("\treturn conn;\n")
                    .append("    }\n").toString();
        }
        if (chkGetterPreparedStatementJDBCDAO.isSelected()) {
            getter = new StringBuilder(getter)
                    .append("\n    public PreparedStatement getPreparedStatement() {\n")
                    .append("\treturn prStm;\n")
                    .append("    }\n").toString();
        }
        if (chkGetterResultSetJDBCDAO.isSelected()) {
            getter = new StringBuilder(getter)
                    .append("\n    public ResultSet getResultSet() {\n")
                    .append("\treturn rs;\n")
                    .append("    }\n").toString();
        }

        if (chkgetAllJDBCDAO.isSelected()) {
            importListAndLinkedList = new StringBuilder("import java.util.List;\n")
                    .append("import java.util.LinkedList;").toString();
        }
        if (chkSetterConnectionJDBCDAO.isSelected()) {
            setter = new StringBuilder(setter)
                    .append("\n    public void setConnection(Connection conn) {\n")
                    .append("\tthis.conn = conn;\n")
                    .append("    }\n").toString();
        }
        if (chkSetterPreparedStatementJDBCDAO.isSelected()) {
            setter = new StringBuilder(setter)
                    .append("\n    public void setPreparedStatement(PreparedStatement prStm) {\n")
                    .append("\tthis.prStm = prStm;\n")
                    .append("    }\n").toString();
        }
        if (chkSetterResultSetJDBCDAO.isSelected()) {
            setter = new StringBuilder(setter)
                    .append("\n    public void setResultSet(ResultSet rs) {\n")
                    .append("\tthis.rs = rs;\n")
                    .append("    }\n").toString();
        }

        if (chkAddJDBCDAO.isSelected()) {
            addMethod = new StringBuilder().append("\n    ")
                    .append(addModifier).append("void persist(Object object) throws SQLException {\n")
                    .append("\tthis.conn = null; //TODO assign with getConnection();\n")
                    .append("\ttry {\n")
                    .append("\t    if (conn != null) {\n")
                    .append("\t        String sql = \"INSERT INTO TODO_TABLE VALUES(?)\";\n")
                    .append("\t        prStm = conn.prepareStatement(sql);\n")
                    .append("\t        prStm.setObject(1, object);\n")
                    .append("\t        prStm.executeUpdate();\n")
                    .append("            }\n")
                    .append("\t} finally {\n")
                    .append("\t    closeConnection();\n")
                    .append("\t}\n")
                    .append("    }\n").toString();
        }
        if (chkUpdateJDBCDAO.isSelected()) {
            updateMethod = new StringBuilder().append("\n    ")
                    .append(updateModifier).append("void merge(Object object) throws SQLException {\n")
                    .append("\tthis.conn = null; //TODO assign with getConnection();\n")
                    .append("\ttry {\n")
                    .append("\t    if (conn != null) {\n")
                    .append("\t        String sql = \"UPDATE TODO_TABLE SET TODO_VAR = ? WHERE TODO_VAR2 = ?\";\n")
                    .append("\t        prStm = conn.prepareStatement(sql);\n")
                    .append("\t        //TODO binding parameters\n")
                    .append("\t        prStm.setObject(1, object);\n")
                    .append("\t        prStm.setObject(2, object);\n")
                    .append("\t        prStm.executeUpdate();\n")
                    .append("            }\n")
                    .append("\t} finally {\n")
                    .append("\t    closeConnection();\n")
                    .append("\t}\n")
                    .append("    }\n").toString();
        }
        if (chkRemoveJDBCDAO.isSelected()) {
            deleteMethod = new StringBuilder("\n    ").append(removeModifier)
                    .append("void remove(Object object) throws SQLException {\n")
                    .append("\tthis.conn = null; //TODO assign with getConnection();\n")
                    .append("\ttry {\n")
                    .append("\t    if (conn != null) {\n")
                    .append("\t        String sql = \"DELETE FROM TODO_TABLE WHERE TODO_VAR = ?\";\n")
                    .append("\t        prStm = conn.prepareStatement(sql);\n")
                    .append("\t        //TODO binding parameters\n")
                    .append("\t        prStm.setObject(1, object);\n")
                    .append("\t        prStm.executeUpdate();\n")
                    .append("            }\n")
                    .append("\t} finally {\n")
                    .append("\t    closeConnection();\n")
                    .append("\t}\n")
                    .append("    }\n").toString();
        }
        if (chkFindJDBCDAO.isSelected()) {
            findMethod = new StringBuilder("\n    ").append(findModifier)
                    .append("Object get(Object object) throws SQLException {\n")
                    .append("\tthis.conn = null; //TODO assign with getConnection();\n")
                    .append("\tObject obj = null; //TODO change the object type\n")
                    .append("\ttry {\n")
                    .append("\t    if (conn != null) {\n")
                    .append("\t        String sql = \"SELECT * FROM TODO_TABLE WHERE TODO_VAR = ?\";\n")
                    .append("\t        prStm = conn.prepareStatement(sql);\n")
                    .append("\t        //TODO binding parameters\n")
                    .append("\t        prStm.setObject(1, object);\n")
                    .append("\t        rs = prStm.executeQuery();\n")
                    .append("\t        if (rs.next()) {\n")
                    .append("\t\t    //TODO change the object type\n")
                    .append("\t\t    obj = rs.getObject(\"TODO-SELECTED\");\n")
                    .append("\t        }\n")
                    .append("            }\n")
                    .append("\t} finally {\n")
                    .append("\t    closeConnection();\n")
                    .append("\t}\n")
                    .append("\treturn obj;\n")
                    .append("    }\n").toString();
        }
        if (chkgetAllJDBCDAO.isSelected()) {
            getAllMethod = new StringBuilder().append("\n    ")
                    .append(getAllModifier).append("List<Object> getAll(Object object) throws SQLException {\n")
                    .append("\tthis.conn = null; //TODO assign with getConnection();\n")
                    .append("\tList<Object> list = null;\n")
                    .append("\ttry {\n")
                    .append("\t    if (conn != null) {\n")
                    .append("\t        String sql = \"SELECT * FROM TODO_TABLE WHERE TODO_VAR = ?\";\n")
                    .append("\t        prStm = conn.prepareStatement(sql);\n")
                    .append("\t        //TODO binding parameters\n")
                    .append("\t        prStm.setObject(1, object);\n")
                    .append("\t        rs = prStm.executeQuery();\n")
                    .append("\t        list = new LinkedList();\n")
                    .append("\t        while (rs.next()) {\n")
                    .append("\t\t    //TODO change the object type\n")
                    .append("\t\t    list.add(rs.getObject(\"TODO-SELECTED\"));\n")
                    .append("\t        }\n")
                    .append("            }\n")
                    .append("\t} finally {\n")
                    .append("\t    closeConnection();\n")
                    .append("\t}\n")
                    .append("\treturn list;\n")
                    .append("    }\n").toString();
        }

        if (chkCloseConnection.isSelected()) {
            importSQLException = "import java.sql.SQLException;";
            closeConnection = new StringBuilder("    ").append(closeConnectionModifier)
                    .append("void closeConnection() {\n")
                    .append("\ttry {\n").toString();
        }
        if (chkConnection.isSelected()) {
            closeConnection = new StringBuilder(closeConnection)
                    .append("\t    if (conn != null) {\n")
                    .append("\t        conn.close();\n")
                    .append("\t    }\n").toString();
        }
        if (chkPreparedStatement.isSelected()) {
            closeConnection = new StringBuilder(closeConnection)
                    .append("\t    if (prStm != null) {\n")
                    .append("\t        prStm.close();\n")
                    .append("\t    }\n").toString();
        }
        if (chkResultSet.isSelected()) {
            closeConnection = new StringBuilder(closeConnection)
                    .append("\t    if (rs != null) {\n")
                    .append("\t        rs.close();\n")
                    .append("\t    }\n").toString();
        }
        if (chkCloseConnection.isSelected()) {
            closeConnection = new StringBuilder(closeConnection)
                    .append("\t} catch (SQLException e) {\n")
                    .append("\t    System.out.println(\"Error at ")
                    .append(txtClassNameJDBCDAO.getText()).append(": \" + e.getMessage());\n")
                    .append("        }\n")
                    .append("    }\n").toString();
        }

        String packagePathName = "";
        if (!txtPackageName.getText().trim().isEmpty()) {
            packagePathName = txtPackageName.getText().trim().replace(".", SYSTEM_SLASH);
        }
        File path = new File(new StringBuilder(SAVE_FILE_PATH).append(packagePathName).toString());
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(
                    new PrintWriter(new StringBuilder(SAVE_FILE_PATH)
                            .append(packagePathName).append(SYSTEM_SLASH)
                            .append(txtClassNameJDBCDAO.getText().trim())
                            .append(".java").toString()));
            bw.newLine();
            bw.write("/**");
            bw.newLine();
            bw.write(" * This class was generated by BangMaple Class Generator");
            bw.newLine();
            bw.write(new StringBuilder(" * Date: ")
                    .append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                            .format(new Date())).toString());
            bw.newLine();
            bw.write(" */");
            if (HAVE_A_PACKAGE_NAME) {
                bw.newLine();
                bw.write(new StringBuilder("package ")
                        .append(txtPackageName.getText().trim())
                        .append(";").toString());
                bw.newLine();
            }
            bw.newLine();
            bw.write(importConnection);
            bw.newLine();
            bw.write(importPreparedStatement);
            bw.newLine();
            bw.write(importResultSet);
            bw.newLine();
            bw.write(importSQLException);
            bw.newLine();
            bw.write(importSerializable);
            bw.newLine();
            bw.write(importListAndLinkedList);
            bw.newLine();
            bw.newLine();
            bw.write(new StringBuilder("public").append(finalModifier)
                    .append(" class ")
                    .append(txtClassNameJDBCDAO.getText()).append(" ")
                    .append(serializableModifier).append("{\n").toString());
            if (chkConnection.isSelected()) {
                bw.newLine();
                bw.write(new StringBuilder("    ")
                        .append(connectionModifier)
                        .append("Connection conn;").toString());
            }
            if (chkPreparedStatement.isSelected()) {
                bw.newLine();
                bw.write(new StringBuilder("    ")
                        .append(preparedStatementModifier)
                        .append("PreparedStatement prStm;").toString());
            }
            if (chkResultSet.isSelected()) {
                bw.newLine();
                bw.write(new StringBuilder("    ").append(resultSetModifier)
                        .append("ResultSet rs;").toString());
            }
            bw.newLine();
            bw.newLine();
            bw.write(closeConnection);
            bw.write(addMethod);
            bw.write(updateMethod);
            bw.write(deleteMethod);
            bw.write(findMethod);
            bw.write(getAllMethod);
            bw.write(getter);
            bw.write(setter);
            bw.newLine();
            bw.write("}");
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Successfully generated this java class file.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while generating class file.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while generating class file.",
                        "Error while generating class", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void btnGenerateJDBCDAOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateJDBCDAOActionPerformed
        if (!validateJDBCDAOClassName()) {
            return;
        }
        selectProjectPath("jdbcDAO");

    }//GEN-LAST:event_btnGenerateJDBCDAOActionPerformed

    private void txtProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProjectNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProjectNameActionPerformed

    private void lblMaxMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaxMinMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_lblMaxMinMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinMouseClicked
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinMouseClicked

    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLogoMouseClicked

    private void lblLogoJDBCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoJDBCMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLogoJDBCMouseClicked
    private void checkEnableCloseConnection() {
        if (!chkConnection.isSelected() && !chkPreparedStatement.isSelected() && !chkResultSet.isSelected()) {
            chkCloseConnection.setSelected(false);
            chkCloseConnection.setEnabled(false);
        }
        if (chkConnection.isSelected() || chkPreparedStatement.isSelected() || chkResultSet.isSelected()) {
            chkCloseConnection.setSelected(true);
            chkCloseConnection.setEnabled(true);
        }
    }
    private void chkConnectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkConnectionItemStateChanged
        // TODO add your handling code here:
        if (chkConnection.isSelected()) {
            chkSetterConnectionJDBCDAO.setEnabled(true);
            chkGetterConnectionJDBCDAO.setEnabled(true);
            chkResultSet.setEnabled(true);
            chkPreparedStatement.setSelected(true);
            chkPreparedStatement.setEnabled(true);
            cbConnectionModifier.setEnabled(true);
        } else {
            chkSetterConnectionJDBCDAO.setSelected(false);
            chkGetterConnectionJDBCDAO.setSelected(false);
            chkSetterConnectionJDBCDAO.setEnabled(false);
            chkGetterConnectionJDBCDAO.setEnabled(false);
            chkResultSet.setSelected(false);
            chkResultSet.setEnabled(false);
            chkPreparedStatement.setSelected(false);
            chkPreparedStatement.setEnabled(false);
            cbConnectionModifier.setEnabled(false);
        }
        checkEnableCloseConnection();
    }//GEN-LAST:event_chkConnectionItemStateChanged

    private void chkPreparedStatementItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPreparedStatementItemStateChanged
        // TODO add your handling code here:
        if (chkPreparedStatement.isSelected()) {
            chkSetterPreparedStatementJDBCDAO.setEnabled(true);
            chkGetterPreparedStatementJDBCDAO.setEnabled(true);
            chkAddJDBCDAO.setEnabled(true);
            chkRemoveJDBCDAO.setEnabled(true);
            chkUpdateJDBCDAO.setEnabled(true);
            chkResultSet.setEnabled(true);
            cbPreparedStatementModifier.setEnabled(true);
            cbResultSetModifier.setEnabled(true);
        } else {
            chkSetterPreparedStatementJDBCDAO.setSelected(false);
            chkGetterPreparedStatementJDBCDAO.setSelected(false);
            chkSetterPreparedStatementJDBCDAO.setEnabled(false);
            chkGetterPreparedStatementJDBCDAO.setEnabled(false);
            chkAddJDBCDAO.setEnabled(false);
            chkAddJDBCDAO.setSelected(false);
            chkRemoveJDBCDAO.setEnabled(false);
            chkRemoveJDBCDAO.setSelected(false);
            chkUpdateJDBCDAO.setEnabled(false);
            chkUpdateJDBCDAO.setSelected(false);
            chkResultSet.setSelected(false);
            chkResultSet.setEnabled(false);
            cbResultSetModifier.setEnabled(false);
            cbPreparedStatementModifier.setEnabled(false);
        }
        checkEnableCloseConnection();
    }//GEN-LAST:event_chkPreparedStatementItemStateChanged

    private void chkResultSetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkResultSetItemStateChanged
        // TODO add your handling code here:
        if (chkResultSet.isSelected()) {
            chkSetterResultSetJDBCDAO.setEnabled(true);
            chkGetterResultSetJDBCDAO.setEnabled(true);
            chkgetAllJDBCDAO.setEnabled(true);
            chkFindJDBCDAO.setEnabled(true);
        } else {
            chkSetterResultSetJDBCDAO.setSelected(false);
            chkGetterResultSetJDBCDAO.setSelected(false);
            chkSetterResultSetJDBCDAO.setEnabled(false);
            chkGetterResultSetJDBCDAO.setEnabled(false);
            chkgetAllJDBCDAO.setSelected(false);
            chkgetAllJDBCDAO.setEnabled(false);
            chkFindJDBCDAO.setSelected(false);
            chkFindJDBCDAO.setEnabled(false);
        }
        checkEnableCloseConnection();
    }//GEN-LAST:event_chkResultSetItemStateChanged

    private void chkCloseConnectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCloseConnectionItemStateChanged
        // TODO add your handling code here:
        if (chkCloseConnection.isSelected()) {
            cbCloseConnectionModifier.setEnabled(true);
        } else {
            cbCloseConnectionModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkCloseConnectionItemStateChanged

    private void chkFindJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFindJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkFindJDBCDAO.isSelected()) {
            cbFindModifier.setEnabled(true);
        } else {
            cbFindModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkFindJDBCDAOItemStateChanged

    private void chkgetAllJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkgetAllJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkgetAllJDBCDAO.isSelected()) {
            cbGetAllModifier.setEnabled(true);
        } else {
            cbGetAllModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkgetAllJDBCDAOItemStateChanged

    private void chkAddJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAddJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkAddJDBCDAO.isSelected()) {
            cbAddModifier.setEnabled(true);
        } else {
            cbAddModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkAddJDBCDAOItemStateChanged

    private void chkRemoveJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkRemoveJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkRemoveJDBCDAO.isSelected()) {
            cbRemoveModifier.setEnabled(true);
        } else {
            cbRemoveModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkRemoveJDBCDAOItemStateChanged

    private void chkUpdateJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkUpdateJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkUpdateJDBCDAO.isSelected()) {
            cbUpdateModifier.setEnabled(true);
        } else {
            cbUpdateModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkUpdateJDBCDAOItemStateChanged

    private boolean validateClassNameDTO() {
        String className = txtClassName.getText();
        if (!className.trim().isEmpty()) {
            String tmpStr = String.valueOf(className.charAt(0));
            if (!tmpStr.matches("^[a-zA-Z]$")) {
                JOptionPane.showMessageDialog(this,
                        "First letter of class name must be alphabet."
                        + "\nPlease try again.");
                return false;
            } else {
                if (!className.matches("^[a-zA-Z0-9\\_]{1,256}$")) {
                    JOptionPane.showMessageDialog(this,
                            "You must follow the class naming format."
                            + "\nPlease try again later.");
                    return false;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please don't leave class name blank.");
            return false;
        }
        return true;
    }

    private boolean validateIfExistExtendingOrImplementing() {
        if (!txtExtendsDTO.getText().trim().isEmpty()) {
            if (!String.valueOf(txtExtendsDTO.getText().trim().charAt(0)).matches("^[a-zA-Z]$")) {
                JOptionPane.showMessageDialog(this,
                        "First letter of class name or package name must be alphabet."
                        + "\nPlease try again.");
                return false;
            } else {
                if (!txtExtendsDTO.getText().trim().matches("^[a-zA-Z0-9\\_]{1,256}$")) {
                    JOptionPane.showMessageDialog(this,
                            "You must follow the class naming format."
                            + "\nPlease try again later.");
                    return false;
                }
            }
        }
        if (!txtImplementsDTO.getText().trim().isEmpty()) {
            if (!String.valueOf(txtImplementsDTO.getText().trim().charAt(0)).matches("^[a-zA-Z]$")) {
                JOptionPane.showMessageDialog(this,
                        "First letter of class name or package name must be alphabet."
                        + "\nPlease try again.");
                return false;
            } else {
                if (!txtImplementsDTO.getText().trim().matches("^[a-zA-Z0-9\\,\\_\\.\\s]{1,256}$")) {
                    JOptionPane.showMessageDialog(this,
                            "You must follow the class naming format."
                            + "\nPlease try again later.");
                    return false;
                }
            }
        }
        return true;
    }

    private void btnGenerateEntityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateEntityActionPerformed
        // TODO add your handling code here:
        if (!validateClassNameDTO()) {
            return;
        }
        if (!validateIfExistExtendingOrImplementing()) {
            return;
        }
        selectProjectPath("dto");
    }//GEN-LAST:event_btnGenerateEntityActionPerformed

    private boolean validateConnection() {
        if (txtDBName.getText().trim().isEmpty()) {
            txtDBName.setText("master");
        }
        if (!txtDBName.getText().matches("^[a-zA-Z0-9]{1,254}$")) {
            JOptionPane.showMessageDialog(this, "Only accept valid character.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtUsername.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave username blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (!txtUsername.getText().matches("^[a-zA-Z0-9]{1,254}$")) {
                JOptionPane.showMessageDialog(this, "Only accept valid character.",
                        "Error while generating class", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (txtPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave password blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtIP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave username blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (!txtIP.getText().matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")) {
                if (!txtIP.getText().matches("^[a-zA-Z0-9\\.]{1,254}$")) {
                    JOptionPane.showMessageDialog(this, "Only accept valid IP Address.",
                            "Error while generating class", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        if (txtPort.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave port blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (!txtIP.getText().matches("^[0-9]{1,5}$")) {
                if (!txtIP.getText().matches("^[a-zA-Z0-9\\.]{1,254}$")) {
                    JOptionPane.showMessageDialog(this, "Only accept valid IP Address.",
                            "Error while generating class", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            try {
                Integer.parseInt(txtPort.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Only accept valid port.",
                        "Error while generating class", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (!txtPackageName.getText().trim().isEmpty()) {
            if (txtPackageName.getText().trim().charAt(0) == '.') {
                JOptionPane.showMessageDialog(this, new StringBuilder("Package name ")
                        .append("musn't be started with a dot. Please try again.").toString());
                return false;
            } else {
                if (!txtPackageName.getText().trim().matches("^[a-zA-Z0-9\\.]{1,256}$")) {
                    JOptionPane.showMessageDialog(this, new StringBuilder("Package ")
                            .append("name is not correct! Please follow the right format.").toString());
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            System.exit(0);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new MainController().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseProject;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnGenerateEntity;
    private javax.swing.JButton btnGenerateJDBCDAO;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbAddModifier;
    private javax.swing.JComboBox<String> cbCloseConnectionModifier;
    private javax.swing.JComboBox<String> cbConnectionModifier;
    private javax.swing.JComboBox<String> cbFindModifier;
    private javax.swing.JComboBox<String> cbGetAllModifier;
    private javax.swing.JComboBox<String> cbGetConnection;
    private javax.swing.JComboBox<String> cbPreparedStatementModifier;
    private javax.swing.JComboBox<String> cbRemoveModifier;
    private javax.swing.JComboBox<String> cbResultSetModifier;
    private javax.swing.JComboBox<String> cbUpdateModifier;
    private javax.swing.JCheckBox chkAddJDBCDAO;
    private javax.swing.JCheckBox chkCloseConnection;
    private javax.swing.JCheckBox chkConnection;
    private javax.swing.JCheckBox chkFinal;
    private javax.swing.JCheckBox chkFinalDTO;
    private javax.swing.JCheckBox chkFinalJDBCDAO;
    private javax.swing.JCheckBox chkFindJDBCDAO;
    private javax.swing.JCheckBox chkGetterConnectionJDBCDAO;
    private javax.swing.JCheckBox chkGetterPreparedStatementJDBCDAO;
    private javax.swing.JCheckBox chkGetterResultSetJDBCDAO;
    private javax.swing.JCheckBox chkImplSerializable;
    private javax.swing.JCheckBox chkPreparedStatement;
    private javax.swing.JCheckBox chkPublic;
    private javax.swing.JCheckBox chkPublicDTO;
    private javax.swing.JCheckBox chkPublicJDBCDAO;
    private javax.swing.JCheckBox chkRemoveJDBCDAO;
    private javax.swing.JCheckBox chkResultSet;
    private javax.swing.JCheckBox chkSerializable;
    private javax.swing.JCheckBox chkSerializableDTO;
    private javax.swing.JCheckBox chkSetterConnectionJDBCDAO;
    private javax.swing.JCheckBox chkSetterPreparedStatementJDBCDAO;
    private javax.swing.JCheckBox chkSetterResultSetJDBCDAO;
    private javax.swing.JCheckBox chkUpdateJDBCDAO;
    private javax.swing.JCheckBox chkgetAllJDBCDAO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLogoJDBC;
    private javax.swing.JLabel lblMaxMin;
    private javax.swing.JLabel lblMin;
    private javax.swing.JTextField txtClassName;
    private javax.swing.JTextField txtClassNameJDBC;
    private javax.swing.JTextField txtClassNameJDBCDAO;
    private javax.swing.JTextField txtDBName;
    private javax.swing.JTextField txtExtendsDTO;
    private javax.swing.JTextField txtGetConnection;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtImplementsDTO;
    private javax.swing.JLabel txtInfo;
    private javax.swing.JTextField txtPackageName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtProjectName;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
