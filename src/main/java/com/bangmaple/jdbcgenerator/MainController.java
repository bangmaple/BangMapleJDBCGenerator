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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author bangmaple
 */
public class MainController extends javax.swing.JFrame {

    private static String SAVE_FILE_PATH = "";
    private static Boolean HAVE_A_PACKAGE_NAME = Boolean.FALSE;
    private static String SYSTEM_SLASH = "/";
    private static String PK = "";
    private static Vector<String> model = new Vector<>();
    private static List<String> pPATH = new ArrayList<>();
    private static Map<String, String> m = new HashMap<>();

    public MainController() {
        initFrame();
        popupMenu();
        loadLogo();
        changeSlash();
        loadConfiguration();
        load();
        loadDefaultNBPCB();
        loadListeners();
    }

    private void loadListeners() {
        txtDefaultNBPP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                updateProjectNameCB();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                updateProjectNameCB();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                updateProjectNameCB();
            }
        });
    }

    private void updateProjectNameCB() {
        load();
    }

    private void initConfiguration(Properties p) {

        txtDefaultNBPP.setText(p.getProperty("defaultNBPP"));
        cbTheme.getModel().setSelectedItem(p.getProperty("theme", "Dark Mode"));
        cbLang.getModel().setSelectedItem(p.getProperty("lang", "en-US"));

        txtIP.setText(p.getProperty("sqlIP", "127.0.0.1"));
        txtPort.setText(p.getProperty("sqlPort", "1433"));
        txtUsername.setText(p.getProperty("sqlUsername", "sa"));
        txtDBName.setText(p.getProperty("sqlDB", "master"));
        txtPassword.setText(p.getProperty("sqlPassword", ""));
    }

    private void loadConfiguration() {
        File f = new File("conf.properties");
        if (f.exists()) {
            if (f.canRead() && f.canWrite()) {
                try {
                    InputStream is = new FileInputStream("conf.properties");
                    Properties p = new Properties();
                    p.load(is);
                    initConfiguration(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initFrame() {
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(null);
        initComponents();
        this.setLocationRelativeTo(null);
        setTableColumnSize();
        addGenericActionListener();
    }

    private void addGenericActionListener() {
        chkPublicAddActionListener();
        chkFinalAddActionListener();
        cbThemeActionListener();
    }

    private void cbThemeActionListener() {
        cbTheme.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attempt();
                setLookAndFeel(String.valueOf(cbTheme.getModel().getSelectedItem()));
                new MainController().setVisible(true);
            }
        });
    }

    private void attempt() {
        this.setVisible(false);
        this.dispose();
    }

    private void chkPublicAddActionListener() {
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
    }

    private void chkFinalAddActionListener() {
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
        new CutCopyPasteActionSupport().setPopup(txtProjectName, txtClassNameJDBC,
                txtClassNameJDBCDAO, txtDBName, txtClassName,
                txtClassNameJDBCDAO, txtPassword, txtUsername, txtPackageName,
                txtExtendsDTO, txtImplementsDTO, txtGetConnection);
    }

    private void loadDefaultNBPCB() {

    }

    private void load() {
        File f;
        model.clear();
        m.clear();
        pPATH.clear();
        cbProjects.removeAllItems();
        if (txtDefaultNBPP.getText().trim().isEmpty()) {
            String user = System.getProperty("user.name");
            if (SYSTEM_SLASH.equals("\\")) {
                f = new File("C:\\Users\\" + user + "\\Documents\\NetBeansProjects\\");
            } else {
                f = new File("/Users/" + user + "/NetBeansProjects/");
            }
        } else {
            f = new File(txtDefaultNBPP.getText() + SYSTEM_SLASH);
        }
        File[] c = f.listFiles(new FileFilter() {
            private boolean parse(File f) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try {
                    builder = factory.newDocumentBuilder();
                    Document document = builder.parse(f);
                    document.getDocumentElement().normalize();
                    Element root = document.getDocumentElement();
                    if (f.getName().equals("build.xml")) {
                        m.put(f.getParent(), root.getAttribute("name"));
                        return true;
                    } else {
                        m.put(f.getParent(), root.getElementsByTagName("artifactId")
                                .item(0).getTextContent());
                        return true;
                    }
                } catch (IOException | ParserConfigurationException | SAXException ex) {
                    ex.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean accept(File file) {
                if (file.canRead() && file.canWrite()) {
                    File ff = new File(file.getAbsolutePath());
                    File[] f = ff.listFiles((File file1, String string) -> {
                        return string.equals("build.xml") || string.equals("pom.xml");
                    });
                    if (f != null && f.length > 0) {
                        if (f[0].getName().equals("build.xml")) {
                            return parse(f[0]);
                        } else if (f[0].getName().equals("pom.xml")) {
                            return parse(f[0]);
                        }
                    }
                }
                return false;
            }
        });

        model.addElement("-- Select a NetBeans (Ant/Maven) project --");
        pPATH.add("");
        m.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            String val = entry.getValue();
            pPATH.add(key);
            model.addElement(val);
        });
        cbProjects.setSelectedIndex(0);
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
        jPanel4 = new javax.swing.JPanel();
        chkCheckDBConnectionEntity = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        chkToStringEntity = new javax.swing.JCheckBox();
        chkHashCodeEntity = new javax.swing.JCheckBox();
        chkEqualsEntity = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        txtClassNameEntity = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        chkPublicEntity = new javax.swing.JCheckBox();
        chkFinalEntity = new javax.swing.JCheckBox();
        btnGenerateEntityDB = new javax.swing.JButton();
        chkImplSerializableEntity = new javax.swing.JCheckBox();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
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
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cbLang = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbTheme = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtDefaultNBPP = new javax.swing.JTextField();
        chkSaveSQLConnection = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        chkSavePassword = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblMaxMin = new javax.swing.JLabel();
        lblMin = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblLogoJDBC = new javax.swing.JLabel();
        txtProjectName = new javax.swing.JTextField();
        txtPackageName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtInfo = new javax.swing.JLabel();
        cbProjects = new javax.swing.JComboBox<>(model);
        jLabel22 = new javax.swing.JLabel();

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGenerateEntity, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Entity (DTO)", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SQL Connection"));

        jLabel3.setText("IP Address:");

        txtIP.setText("127.0.0.1");

        jLabel4.setText("Port:");

        txtPort.setText("1433");

        jLabel5.setText("Username:");

        txtUsername.setText("sa");

        jLabel6.setText("Password:");

        jLabel7.setText("Database name:");

        txtDBName.setText("master");

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
                .addGap(61, 61, 61)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPort, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(txtPassword))
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
                    .addComponent(txtDBName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(38, Short.MAX_VALUE))
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
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("JDBC Utils", jPanel2);

        chkCheckDBConnectionEntity.setText("Got connection from \"Check connection\" of JDBC Utils");
        chkCheckDBConnectionEntity.setEnabled(false);
        chkCheckDBConnectionEntity.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCheckDBConnectionEntityItemStateChanged(evt);
            }
        });

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Override method"));

        chkToStringEntity.setText("toString()");
        chkToStringEntity.setEnabled(false);

        chkHashCodeEntity.setText("hashCode()");
        chkHashCodeEntity.setEnabled(false);

        chkEqualsEntity.setText("equals()");
        chkEqualsEntity.setEnabled(false);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkHashCodeEntity)
                            .addComponent(chkToStringEntity))
                        .addGap(120, 120, 120))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(chkEqualsEntity)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkToStringEntity)
                .addGap(18, 18, 18)
                .addComponent(chkHashCodeEntity)
                .addGap(18, 18, 18)
                .addComponent(chkEqualsEntity)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.setEnabled(false);
        jScrollPane3.setViewportView(jTable3);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Class generation"));

        txtClassNameEntity.setText("GenericDTO");
        txtClassNameEntity.setEnabled(false);

        jLabel21.setText("Class name:");

        chkPublicEntity.setSelected(true);
        chkPublicEntity.setText("public");
        chkPublicEntity.setEnabled(false);

        chkFinalEntity.setText("final");
        chkFinalEntity.setEnabled(false);

        btnGenerateEntityDB.setText("Select project path and generate...");
        btnGenerateEntityDB.setEnabled(false);
        btnGenerateEntityDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateEntityDBActionPerformed(evt);
            }
        });

        chkImplSerializableEntity.setSelected(true);
        chkImplSerializableEntity.setText("impl. Serializable");
        chkImplSerializableEntity.setEnabled(false);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(29, 29, 29)
                        .addComponent(txtClassNameEntity))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(chkPublicEntity)
                        .addGap(18, 18, 18)
                        .addComponent(chkFinalEntity)
                        .addGap(18, 18, 18)
                        .addComponent(chkImplSerializableEntity)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerateEntityDB, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClassNameEntity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(21, 21, 21)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkPublicEntity)
                    .addComponent(chkFinalEntity)
                    .addComponent(btnGenerateEntityDB)
                    .addComponent(chkImplSerializableEntity))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("User guide"));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Please execute the \"Check connection\" button from JDBC Utils then the application will automatically translate the Table from SQL Server Database to Entity (DTO) for you.\nYou can edit any property or class name if you want.\n\nClick the \"Select project path and generate\" to finish.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chkCheckDBConnectionEntity)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkCheckDBConnectionEntity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Entity using DB", jPanel4);

        jLabel8.setText("Coming soon...");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(633, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(466, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DAO using DB", jPanel14);

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
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("JDBC DAO", jPanel6);

        jLabel19.setText("Language:");

        cbLang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "en-US", "vi-VN" }));

        jLabel20.setText("Theme:");

        cbTheme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dark Mode", "White Mode", "Ocean Mode" }));

        jLabel17.setText("<html>fb.com/BangMaple<br/>github.com/BangMaple</html>");

        jButton1.setText("Choose...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel23.setText("NetBeansProjects path:");

        txtDefaultNBPP.setEditable(false);

        chkSaveSQLConnection.setText("Save SQL Connection string for the next time");

        jButton3.setText("Save this configuration");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        chkSavePassword.setText("Also save SQL Connection password (not recommended)");

        jCheckBox3.setText("Save JDBC Utils configuration");

        jCheckBox4.setText("Save JDBC DAO configuration");

        jCheckBox5.setText("Save Entity (DTO) configuration");

        jLabel24.setText("If the path is blank, the tool will choose the default NetBeansProjects path:");

        jLabel25.setText(" - for Windows: C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\NetBeansProjects\\");

            jLabel26.setText(" - for macOS: /Users/"+System.getProperty("user.name")+"/NetBeansProjects/");

            jLabel27.setText("Please click the button \"Choose\" if you want to change the path for the Project name combo box.");

            jLabel28.setText("You will lose the current configuration if you don't attempt to save this.");

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addGap(18, 18, 18)
                            .addComponent(cbLang, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                            .addComponent(jLabel20)
                            .addGap(18, 18, 18)
                            .addComponent(cbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(chkSaveSQLConnection)
                        .addComponent(chkSavePassword)
                        .addComponent(jCheckBox3)
                        .addComponent(jCheckBox4)
                        .addComponent(jCheckBox5)
                        .addComponent(jLabel24)
                        .addComponent(jLabel25)
                        .addComponent(jLabel26)
                        .addComponent(jLabel27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDefaultNBPP, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))
                        .addComponent(jLabel28))
                    .addContainerGap(106, Short.MAX_VALUE))
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel24)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel25)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel26)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel27)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txtDefaultNBPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(cbLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addGap(18, 18, 18)
                    .addComponent(chkSaveSQLConnection)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(chkSavePassword)
                    .addGap(18, 18, 18)
                    .addComponent(jCheckBox3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckBox4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckBox5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                    .addComponent(jLabel28)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3))
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Configuration", jPanel5);

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

            cbProjects.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                    cbProjectsItemStateChanged(evt);
                }
            });

            jLabel22.setText("Project name:");

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
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(lblMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel13))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtPackageName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                                        .addComponent(txtProjectName)
                                        .addComponent(cbProjects, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(lblMin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbProjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtProjectName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtPackageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10))
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

    private boolean closeDBConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    private boolean checkDBConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(new StringBuilder("jdbc:sqlserver://")
                    .append(txtIP.getText()).append(":").append(txtPort.getText())
                    .append(";databaseName=").append(txtDBName.getText())
                    .append(";username=").append(txtUsername.getText())
                    .append(";password=")
                    .append(txtPassword.getPassword()).toString());
        } catch (SQLException | ClassNotFoundException e) {
        }
        return closeDBConnection(conn);
    }

    private void closeBufferedWriter(final BufferedWriter bw) {
        try {
            if (bw != null) {
                bw.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while generating class file.", "Error while generating class", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateJDBCUtils() {
        String publicModifier = "";
        String implementsSerializable = "";
        String serializable = "";
        String finalModifier = "";
        String getConnectionModifier = new StringBuilder(
                String.valueOf(cbGetConnection.getModel().getSelectedItem()))
                .append(" ").toString();
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
            generateBangMapleIntroductionText(bw);
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
                    .append(txtPassword.getPassword()).append("\");").toString());
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
            errorGeneratingClassMessage();
        } finally {
            closeBufferedWriter(bw);
        }
    }

    private List<String> getDataFromDTOTableToVarList() {
        final DefaultTableModel tblModel = (DefaultTableModel) jTable1.getModel();
        int rowCount = tblModel.getRowCount();
        List<String> varList = new ArrayList<>(3);
        for (int i = 0; i < rowCount; i++) {
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
        return varList;
    }

    private String generateGetterDataFromDTOTable(final String type, final String name) {
        return new StringBuilder("\n    public ").append(type).append(" get")
                .append(name.substring(0, 1).toUpperCase())
                .append(name.substring(1)).append("() {\n").append("\treturn ")
                .append(name).append(";\n").append("    }\n").toString();
    }

    private String generateSetterDataFromDTOTable(final String type, final String name) {
        return new StringBuilder("\n    public void set")
                .append(name.substring(0, 1).toUpperCase())
                .append(name.substring(1)).append("(").append(type).append(" ")
                .append(name).append(") {\n").append("\tthis.").append(name)
                .append(" = ").append(name).append(";\n").append("    }\n")
                .toString();
    }

    private List<String>[] getDataFromDTOTable() {
        List<String> varList = getDataFromDTOTableToVarList();
        int size = varList.size();
        List<String> attributeList = new ArrayList<>();
        List<String> getterList = new ArrayList<>();
        List<String> setterList = new ArrayList<>();
        List<String> constructorList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            StringTokenizer stk = new StringTokenizer(varList.get(i), " ");
            while (stk.hasMoreTokens()) {
                String getter = null;
                String setter = null;
                String var = null;
                if (stk.countTokens() == 6) {
                    var = new StringBuilder(stk.nextToken()).append(" ").toString();
                }
                String type = stk.nextToken();
                String name = stk.nextToken();
                if (var == null) {
                    var = "";
                }
                var = new StringBuilder(var).append(type).append(" ").append(name)
                        .append(";").toString();
                if (stk.nextToken().equals("true")) {
                    getter = generateGetterDataFromDTOTable(type, name);
                }

                if (stk.nextToken().equals("true")) {
                    setter = generateSetterDataFromDTOTable(type, name);
                }
                attributeList.add(var);
                getterList.add(getter);
                setterList.add(setter);
                if (stk.nextToken().equals("true")) {
                    constructorList.add(new StringBuilder(type).append(" ").append(name).toString());
                }
            }
        }
        return new List[]{attributeList, getterList, setterList, constructorList};
    }

    private String getGetterDTO(final List<String> getterList) {
        String getter = "";
        int size = getterList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                getter = new StringBuilder(getter).append(getterList.get(i)).toString();
            }
        }
        return getter;
    }

    private String getSetterDTO(final List<String> setterList) {
        String setter = "";
        int size = setterList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                setter = new StringBuilder(setter).append(setterList.get(i)).toString();
            }
        }
        return setter;
    }

    private String getAttributesDTO(final List<String> attributeList) {
        String var = "";
        int size = attributeList.size();
        for (int i = 0; i < size; i++) {
            var = new StringBuilder(var).append("    ").append(attributeList.get(i))
                    .append("\n").toString();
        }
        return var;
    }

    private String getConstructorDTO(final List<String> constructorList) {
        final String className = txtClassName.getText().trim();
        String constructor = new StringBuilder("    public ")
                .append(className).append("(").toString();
        int size = constructorList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String str = constructorList.get(i);
                if (i != (size - 1)) {
                    constructor = new StringBuilder(constructor)
                            .append(str).append(", ").toString();
                } else {
                    constructor = new StringBuilder(constructor)
                            .append(str).toString();
                }
            }
            constructor = new StringBuilder(constructor).append(") {\n").toString();
            for (int i = 0; i < size; i++) {
                String str = constructorList.get(i);
                String testVar = str.substring(str.lastIndexOf(" ") + 1, str.length());
                constructor = new StringBuilder(constructor).append("\tthis.")
                        .append(testVar).append(" = ").append(testVar)
                        .append(";\n").toString();
            }
            constructor = new StringBuilder(constructor).append("    }\n\n")
                    .append("    public ").append(className).append("() {\n")
                    .append("    }").toString();

        } else {
            constructor = new StringBuilder(constructor).append(") {\n")
                    .append("    }").toString();
        }
        return constructor;
    }

    private void generateDTO() {
        List<String>[] list = getDataFromDTOTable();
        List<String> attributeList = list[0];
        List<String> getterList = list[1];
        List<String> setterList = list[2];
        List<String> constructorList = list[3];
        String var = getAttributesDTO(attributeList);
        String getter = getGetterDTO(getterList);
        String setter = getSetterDTO(setterList);
        String constructor = getConstructorDTO(constructorList);
        String publicModifier = "";
        String implementsSerializable = "";
        String serializable = "";
        String finalModifier = "";
        String extendClass = "";
        final String extendsDTOText = txtExtendsDTO.getText().trim();
        if (!extendsDTOText.isEmpty()) {
            extendClass = new StringBuilder(" extends ").append(extendsDTOText).toString();
        }

        if (chkPublicDTO.isSelected()) {
            publicModifier = "public ";
        }
        if (chkSerializableDTO.isSelected()) {
            implementsSerializable = " implements Serializable";
            serializable = "import java.io.Serializable;";
        }
        final String implementsDTOText = txtImplementsDTO.getText().trim();
        if (!implementsDTOText.isEmpty()) {
            if (implementsSerializable.isEmpty()) {
                implementsSerializable = " implements";
            }
            if (implementsDTOText.contains(",")) {
                String[] split = implementsDTOText.split(",");
                int splitedImplDTOTextSize = split.length;
                for (int i = 0; i < splitedImplDTOTextSize; i++) {
                    if (i == 0 && serializable.isEmpty()) {
                        implementsSerializable = new StringBuilder(implementsSerializable)
                                .append(" ").append(split[i].trim()).toString();
                    } else {
                        implementsSerializable = new StringBuilder(implementsSerializable)
                                .append(", ").append(split[i].trim()).toString();
                    }
                }
            } else {
                if (!serializable.isEmpty()) {
                    implementsSerializable = new StringBuilder(implementsSerializable)
                            .append(", ").append(implementsDTOText).toString();
                } else {
                    implementsSerializable = new StringBuilder(implementsSerializable)
                            .append(implementsDTOText).toString();
                }
            }
        }
        if (chkFinalDTO.isSelected()) {
            finalModifier = "final ";
        }
        BufferedWriter bw = null;
        String packagePathName = "";
        final String packageName = txtPackageName.getText().trim();
        if (!packageName.isEmpty()) {
            packagePathName = packageName.replace(".", SYSTEM_SLASH);
        }
        File path = new File(new StringBuilder(SAVE_FILE_PATH)
                .append(packagePathName).toString());
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        final String className = txtClassName.getText().trim();
        try {
            final String classGeneratingPath = new StringBuilder(SAVE_FILE_PATH)
                    .append(packagePathName).append(SYSTEM_SLASH)
                    .append(className).append(".java").toString();
            bw = new BufferedWriter(new PrintWriter(classGeneratingPath));
            bw.newLine();
            generateBangMapleIntroductionText(bw);
            if (HAVE_A_PACKAGE_NAME) {
                bw.newLine();
                bw.write(new StringBuilder("package ")
                        .append(txtPackageName.getText().trim()).append(";").toString());
                bw.newLine();
            }
            if (!implementsSerializable.isEmpty()) {
                bw.newLine();
                bw.write(serializable);
                bw.newLine();
            }
            bw.newLine();
            bw.write(new StringBuilder(publicModifier).append(finalModifier)
                    .append("class ").append(className).append(extendClass)
                    .append(implementsSerializable).append(" {").toString());
            bw.newLine();
            bw.write(new StringBuilder("\n").append(var).toString());
            bw.newLine();
            bw.write(constructor);
            bw.newLine();
            bw.write(getter);
            bw.write(setter);
            bw.newLine();
            bw.write("}");
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Successfully generated this java class file.");
        } catch (IOException ex) {
            errorGeneratingClassMessage();
        } finally {
            closeBufferedWriter(bw);
        }
    }

    private void errorGeneratingClassMessage() {
        JOptionPane.showMessageDialog(this, "Error while generating class file.",
                "Error while generating class", JOptionPane.ERROR_MESSAGE);
    }

    private void generateBangMapleIntroductionText(final BufferedWriter bw) throws IOException {
        bw.write("/**");
        bw.newLine();
        bw.write(" * This class was generated by BangMaple Class Generator");
        bw.newLine();
        bw.write(new StringBuilder(" * Date: ").append(new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss").format(new Date())).toString());
        bw.newLine();
        bw.write(" */");
    }

    private void chooseSaveDialogStrategy(final MyFileChooser chooser, final String type) {
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        switch (type) {
            case "jdbcUtils":
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassNameJDBC.getText()).append(".java").toString()));
                chooser.setFileFilter(new FileNameExtensionFilter("Only accept NetBeans project folder!",
                        "java"));
                break;
            case "jdbcDAO":
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassNameJDBCDAO.getText()).append(".java").toString()));
                chooser.setFileFilter(new FileNameExtensionFilter("Only accept NetBeans project folder!",
                        "java"));
                break;
            case "dbentity":
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassNameEntity.getText()).append(".java").toString()));
                chooser.setFileFilter(new FileNameExtensionFilter("Only accept NetBeans project folder!",
                        "java"));
                break;
            case "projectPath":
                chooser.setSelectedFile(new File("Do not change this"));
                chooser.setFileFilter(new FileNameExtensionFilter("Only accept folder containing NetBeans projects!",
                        "java"));
                break;
            default:
                chooser.setSelectedFile(new File(new StringBuilder(
                        txtClassName.getText()).append(".java").toString()));
                chooser.setFileFilter(new FileNameExtensionFilter("Only accept NetBeans project folder!",
                        "java"));
                break;
        }

    }

    private void addSaveDialogActionListener(MyFileChooser chooser, JDialog dialog) {
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
    }

    private void saveNBPojectsDialogActionListener(MyFileChooser chooser, JDialog dialog) {
        chooser.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFileChooser chooser = (JFileChooser) evt.getSource();
                if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
                    File f = chooser.getSelectedFile().getParentFile();

                  //  if (SYSTEM_SLASH.equals("\\")) {
              //          f = f.getParentFile();
               //     }

                    String projectPath = "";
                    if (f.isDirectory()) {
                        projectPath = f.getAbsolutePath();
                        JOptionPane.showMessageDialog(null, "Successfully chose the default NetBeansProjects path!");
                    } else {

                        JOptionPane.showMessageDialog(null, "This is not a directory. Please try again.");
                    }
                    txtDefaultNBPP.setText(projectPath);
                    dialog.setVisible(false);
                } else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
                    dialog.setVisible(false);
                }
            }
        });
    }

    private void addDialogWindowListener(JDialog dialog) {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });
    }

    private void initSaveDialog(String type) {
        MyFileChooser chooser = new MyFileChooser(".");
        chooseSaveDialogStrategy(chooser, type);
        final JDialog dialog = chooser.createDialog(null);
        if (type.equals("projectPath")) {
            saveNBPojectsDialogActionListener(chooser, dialog);
        } else {
            addSaveDialogActionListener(chooser, dialog);
        }
        addDialogWindowListener(dialog);
        dialog.setVisible(true);
    }

    private void setNetBeansProjectType(final String type) {
        switch (type) {
            case "jdbcUtils":
                generateJDBCUtils();
                break;
            case "jdbcDAO":
                generateJDBCDAO();
                break;
            case "dbentity":
                generateDBEntity();
                break;
            default:
                generateDTO();
                break;
        }
    }

    private void checkProjectPathBeforeUse(final String type) {
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
            String tmp = SAVE_FILE_PATH;
            if (f2.isFile()) {
                SAVE_FILE_PATH = new StringBuilder(SAVE_FILE_PATH)
                        .append("src").append(SYSTEM_SLASH).append("main")
                        .append(SYSTEM_SLASH).append("java")
                        .append(SYSTEM_SLASH).toString();
            } else {
                SAVE_FILE_PATH = new StringBuilder(SAVE_FILE_PATH)
                        .append("src").append(SYSTEM_SLASH).toString();
            }
            setNetBeansProjectType(type);
            SAVE_FILE_PATH = tmp;
        } else {
            txtProjectName.setText("");
            JOptionPane.showMessageDialog(this,
                    new StringBuilder("Only accept NetBeansProjects's project folder.")
                            .append("\nJava with Ant or Maven is acceptable!").toString());
        }
    }

    private void setSaveFilePath(boolean check, String type) {
        if (!check) {
            initSaveDialog(type);
            checkProjectPathBeforeUse(type);
        } else {
            //     System.out.println("check before true: "  + SAVE_FILE_PATH);
            //    SAVE_FILE_PATH = new StringBuilder(SAVE_FILE_PATH.substring(0,
            //           SAVE_FILE_PATH.lastIndexOf(SYSTEM_SLASH)))
            //         .append(SYSTEM_SLASH).toString();
            //    System.out.println("check true: " + SAVE_FILE_PATH);
            checkProjectPathBeforeUse(type);
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
        setSaveFilePath(check, type);
    }

    private String generateGetterConnectionJDBCDAO(final String getter) {
        return new StringBuilder(getter)
                .append("\n    public Connection getConnection() {\n")
                .append("\treturn conn;\n")
                .append("    }\n").toString();
    }

    private String generateGetterPreparedStatementJDBCDAO(final String getter) {
        return new StringBuilder(getter)
                .append("\n    public PreparedStatement getPreparedStatement() {\n")
                .append("\treturn prStm;\n")
                .append("    }\n").toString();
    }

    private String generateGetterResultSetJDBCDAO(final String getter) {
        return new StringBuilder(getter)
                .append("\n    public ResultSet getResultSet() {\n")
                .append("\treturn rs;\n")
                .append("    }\n").toString();
    }

    private String generateSetterConnectionJDBCDAO(final String setter) {
        return new StringBuilder(setter)
                .append("\n    public void setConnection(Connection conn) {\n")
                .append("\tthis.conn = conn;\n")
                .append("    }\n").toString();
    }

    private String generateSetterPreparedStatementJDBCDAO(final String setter) {
        return new StringBuilder(setter)
                .append("\n    public void setPreparedStatement(PreparedStatement prStm) {\n")
                .append("\tthis.prStm = prStm;\n")
                .append("    }\n").toString();
    }

    private String generateSetterResultSetJDBCDAO(final String setter) {
        return new StringBuilder(setter)
                .append("\n    public void setResultSet(ResultSet rs) {\n")
                .append("\tthis.rs = rs;\n")
                .append("    }\n").toString();
    }

    private String generateAddMethodJDBCDAO(final String addModifier) {
        return new StringBuilder().append("\n    ")
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

    private String generateUpdateMethodJDBCDAO(final String updateModifier) {
        return new StringBuilder().append("\n    ")
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

    private String generateRemoveMethodJDBCDAO(final String removeModifier) {
        return new StringBuilder("\n    ").append(removeModifier)
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

    private String generateFindMethodJDBCDAO(final String findModifier) {
        return new StringBuilder("\n    ").append(findModifier)
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

    private String generateGetAllMethodJDBCDAO(final String getAllModifier) {
        return new StringBuilder().append("\n    ")
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

    private String generateCloseConnectionJDBCDAO(final String closeConnectionModifier) {
        return new StringBuilder("    ").append(closeConnectionModifier)
                .append("void closeConnection() {\n")
                .append("\ttry {\n").toString();
    }

    private String generateImportListAndLinkedList() {
        return new StringBuilder("import java.util.List;\n")
                .append("import java.util.LinkedList;").toString();
    }

    private String generateCloseConnectionConnectionMethodJDBCDAO(final String closeConnection) {
        return new StringBuilder(closeConnection)
                .append("\t    if (conn != null) {\n")
                .append("\t        conn.close();\n")
                .append("\t    }\n").toString();
    }

    private String generateCloseConnectionPreparedStatementMethodJDBCDAO(final String closeConnection) {
        return new StringBuilder(closeConnection)
                .append("\t    if (prStm != null) {\n")
                .append("\t        prStm.close();\n")
                .append("\t    }\n").toString();
    }

    private String generateCloseConnectionResultSetMethodJDBCDAO(final String closeConnection) {
        return new StringBuilder(closeConnection)
                .append("\t    if (rs != null) {\n")
                .append("\t        rs.close();\n")
                .append("\t    }\n").toString();
    }

    private String generateCloseConnectionCatchBlockJDBCDAO(final String closeConnection) {
        return new StringBuilder(closeConnection)
                .append("\t} catch (SQLException e) {\n")
                .append("\t    System.out.println(\"Error at ")
                .append(txtClassNameJDBCDAO.getText()).append(": \" + e.getMessage());\n")
                .append("        }\n")
                .append("    }\n").toString();
    }

    private void generateJDBCDAO() {
        if (!validateJDBCDAOClassName()) {
            return;
        }
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

        String connectionModifier = new StringBuilder(
                String.valueOf(cbConnectionModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String preparedStatementModifier = new StringBuilder(
                String.valueOf(cbPreparedStatementModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String resultSetModifier = new StringBuilder(
                String.valueOf(cbResultSetModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String addModifier = new StringBuilder(
                String.valueOf(cbAddModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String findModifier = new StringBuilder(
                String.valueOf(cbFindModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String removeModifier = new StringBuilder(
                String.valueOf(cbRemoveModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String getAllModifier = new StringBuilder(
                String.valueOf(cbGetAllModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String updateModifier = new StringBuilder(
                String.valueOf(cbUpdateModifier.getModel().getSelectedItem()))
                .append(" ").toString();
        String closeConnectionModifier = new StringBuilder(
                String.valueOf(cbCloseConnectionModifier.getModel().getSelectedItem()))
                .append(" ").toString();

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
            getter = generateGetterConnectionJDBCDAO(getter);
        }
        if (chkGetterPreparedStatementJDBCDAO.isSelected()) {
            getter = generateGetterPreparedStatementJDBCDAO(getter);
        }
        if (chkGetterResultSetJDBCDAO.isSelected()) {
            getter = generateGetterResultSetJDBCDAO(getter);
        }

        if (chkgetAllJDBCDAO.isSelected()) {
            importListAndLinkedList = generateImportListAndLinkedList();
        }
        if (chkSetterConnectionJDBCDAO.isSelected()) {
            setter = generateSetterConnectionJDBCDAO(setter);
        }
        if (chkSetterPreparedStatementJDBCDAO.isSelected()) {
            setter = generateSetterPreparedStatementJDBCDAO(setter);
        }
        if (chkSetterResultSetJDBCDAO.isSelected()) {
            setter = generateSetterResultSetJDBCDAO(setter);
        }

        if (chkAddJDBCDAO.isSelected()) {
            addMethod = generateAddMethodJDBCDAO(addModifier);
        }
        if (chkUpdateJDBCDAO.isSelected()) {
            updateMethod = generateUpdateMethodJDBCDAO(updateModifier);
        }
        if (chkRemoveJDBCDAO.isSelected()) {
            deleteMethod = generateRemoveMethodJDBCDAO(removeModifier);
        }
        if (chkFindJDBCDAO.isSelected()) {
            findMethod = generateFindMethodJDBCDAO(findModifier);
        }
        if (chkgetAllJDBCDAO.isSelected()) {
            getAllMethod = generateGetAllMethodJDBCDAO(getAllModifier);
        }

        if (chkCloseConnection.isSelected()) {
            importSQLException = "import java.sql.SQLException;";
            closeConnection = generateCloseConnectionJDBCDAO(closeConnectionModifier);
        }
        if (chkConnection.isSelected()) {
            closeConnection = generateCloseConnectionConnectionMethodJDBCDAO(closeConnection);
        }
        if (chkPreparedStatement.isSelected()) {
            closeConnection = generateCloseConnectionPreparedStatementMethodJDBCDAO(closeConnection);
        }
        if (chkResultSet.isSelected()) {
            closeConnection = generateCloseConnectionResultSetMethodJDBCDAO(closeConnection);
        }
        if (chkCloseConnection.isSelected()) {
            closeConnection = generateCloseConnectionCatchBlockJDBCDAO(closeConnection);
        }

        String packagePathName = "";
        final String packageName = txtPackageName.getText().trim();
        if (!packageName.isEmpty()) {
            packagePathName = packageName.replace(".", SYSTEM_SLASH);
        }
        File path = new File(new StringBuilder(SAVE_FILE_PATH)
                .append(packagePathName).toString());
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
            generateBangMapleIntroductionText(bw);
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
            errorGeneratingClassMessage();
        } finally {
            closeBufferedWriter(bw);
        }
    }


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

    private void chkCloseConnectionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCloseConnectionItemStateChanged
        // TODO add your handling code here:
        if (chkCloseConnection.isSelected()) {
            cbCloseConnectionModifier.setEnabled(true);
        } else {
            cbCloseConnectionModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkCloseConnectionItemStateChanged

    private void chkgetAllJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkgetAllJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkgetAllJDBCDAO.isSelected()) {
            cbGetAllModifier.setEnabled(true);
        } else {
            cbGetAllModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkgetAllJDBCDAOItemStateChanged

    private void chkUpdateJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkUpdateJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkUpdateJDBCDAO.isSelected()) {
            cbUpdateModifier.setEnabled(true);
        } else {
            cbUpdateModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkUpdateJDBCDAOItemStateChanged

    private void chkFindJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFindJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkFindJDBCDAO.isSelected()) {
            cbFindModifier.setEnabled(true);
        } else {
            cbFindModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkFindJDBCDAOItemStateChanged

    private void chkRemoveJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkRemoveJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkRemoveJDBCDAO.isSelected()) {
            cbRemoveModifier.setEnabled(true);
        } else {
            cbRemoveModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkRemoveJDBCDAOItemStateChanged

    private void chkAddJDBCDAOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAddJDBCDAOItemStateChanged
        // TODO add your handling code here:
        if (chkAddJDBCDAO.isSelected()) {
            cbAddModifier.setEnabled(true);
        } else {
            cbAddModifier.setEnabled(false);
        }
    }//GEN-LAST:event_chkAddJDBCDAOItemStateChanged

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

    private void btnGenerateJDBCDAOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateJDBCDAOActionPerformed
        if (!validateJDBCDAOClassName()) {
            return;
        }
        selectProjectPath("jdbcDAO");
    }//GEN-LAST:event_btnGenerateJDBCDAOActionPerformed

    private void enableDBEntityProperties() {
        chkCheckDBConnectionEntity.setSelected(true);
    }

    private void enableDBEntityDAOProperties() {

    }

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        if (validateConnection()) {
            if (checkDBConnection()) {
                txtInfo.setText("(+) Successfully connected to the given database name.");
                enableDBEntityProperties();
                getPropertiesFromDBForEntity();
                enableDBEntityDAOProperties();
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

    private void btnChooseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseProjectActionPerformed
        if (!validateConnection()) {
            return;
        }
        if (!validateJDBCUtilsData()) {
            return;
        }
        selectProjectPath("jdbcUtils");
    }//GEN-LAST:event_btnChooseProjectActionPerformed

    private void btnGenerateEntityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateEntityActionPerformed
        if (!validateClassNameDTO()) {
            return;
        }
        if (!validateIfExistExtendingOrImplementing()) {
            return;
        }
        selectProjectPath("dto");
    }//GEN-LAST:event_btnGenerateEntityActionPerformed

    private boolean validateEntityDBClassName() {
        return true;
    }

    private List<String> getDataFromDBEntityTableToVarList() {
        final DefaultTableModel tblModel = (DefaultTableModel) jTable3.getModel();
        int rowCount = tblModel.getRowCount();
        List<String> varList = new ArrayList<>(3);
        for (int i = 0; i < rowCount; i++) {
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
        return varList;
    }

    private List<String>[] getDataFromDBEntityTable() {
        List<String> varList = getDataFromDBEntityTableToVarList();
        int size = varList.size();
        List<String> attributeList = new ArrayList<>();
        List<String> getterList = new ArrayList<>();
        List<String> setterList = new ArrayList<>();
        List<String> constructorList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            StringTokenizer stk = new StringTokenizer(varList.get(i), " ");
            while (stk.hasMoreTokens()) {
                String getter = null;
                String setter = null;
                String var = null;
                if (stk.countTokens() == 6) {
                    var = new StringBuilder(stk.nextToken()).append(" ").toString();
                }
                String type = stk.nextToken();
                String name = stk.nextToken();
                if (var == null) {
                    var = "";
                }
                var = new StringBuilder(var).append(type).append(" ").append(name)
                        .append(";").toString();
                if (stk.nextToken().equals("true")) {
                    getter = generateGetterDataFromDTOTable(type, name);
                }

                if (stk.nextToken().equals("true")) {
                    setter = generateSetterDataFromDTOTable(type, name);
                }
                attributeList.add(var);
                getterList.add(getter);
                setterList.add(setter);
                if (stk.nextToken().equals("true")) {
                    constructorList.add(new StringBuilder(type).append(" ").append(name).toString());
                }
            }
        }
        return new List[]{attributeList, getterList, setterList, constructorList};
    }

    private String getConstructorDBEntity(final List<String> constructorList) {
        final String className = txtClassNameEntity.getText().trim();
        String constructor = new StringBuilder("    public ")
                .append(className).append("(").toString();
        int size = constructorList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String str = constructorList.get(i);
                if (i != (size - 1)) {
                    constructor = new StringBuilder(constructor)
                            .append(str).append(", ").toString();
                } else {
                    constructor = new StringBuilder(constructor)
                            .append(str).toString();
                }
            }
            constructor = new StringBuilder(constructor).append(") {\n").toString();
            for (int i = 0; i < size; i++) {
                String str = constructorList.get(i);
                String testVar = str.substring(str.lastIndexOf(" ") + 1, str.length());
                constructor = new StringBuilder(constructor).append("\tthis.")
                        .append(testVar).append(" = ").append(testVar)
                        .append(";\n").toString();
            }
            constructor = new StringBuilder(constructor).append("    }\n\n")
                    .append("    public ").append(className).append("() {\n")
                    .append("    }").toString();

        } else {
            constructor = new StringBuilder(constructor).append(") {\n")
                    .append("    }").toString();
        }
        return constructor;
    }

    private void generateDBEntity() {
        List<String>[] list = getDataFromDBEntityTable();
        List<String> attributeList = list[0];
        List<String> getterList = list[1];
        List<String> setterList = list[2];
        List<String> constructorList = list[3];
        String var = getAttributesDTO(attributeList);
        String getter = getGetterDTO(getterList);
        String setter = getSetterDTO(setterList);
        String constructor = getConstructorDBEntity(constructorList);
        String publicModifier = "";
        String serializable = "";
        String finalModifier = "";
        String implementsSerializable = "";
        String importJavaDate = "";
        for (int i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).contains("Date")) {
                importJavaDate = "import java.util.Date;";
                break;
            }
        }
        if (chkPublicEntity.isSelected()) {
            publicModifier = "public ";
        }
        if (chkFinalEntity.isSelected()) {
            finalModifier = "final ";
        }
        if (chkImplSerializableEntity.isSelected()) {
            serializable = "import java.io.Serializable;";
            implementsSerializable = " implements Serializable";
        }

        BufferedWriter bw = null;
        String packagePathName = "";
        final String packageName = txtPackageName.getText().trim();
        if (!packageName.isEmpty()) {
            packagePathName = packageName.replace(".", SYSTEM_SLASH);
        }
        File path = new File(new StringBuilder(SAVE_FILE_PATH)
                .append(packagePathName).toString());
        if (!path.isDirectory()) {
            path.mkdirs();
        }
        final String className = txtClassNameEntity.getText().trim();
        final String hashCode = new StringBuilder("    @Override\n")
                .append("    public int hashCode() {\n")
                .append("\tint hash = 0;\n")
                .append("\thash += (").append(PK).append(" != null ? ")
                .append(PK).append(".hashCode() : 0);\n")
                .append("\treturn hash;\n")
                .append("    }\n").toString();
        final String toString = new StringBuilder("    @Override\n")
                .append("    public String toString() {\n")
                .append("\treturn \"").append(className)
                .append("[ ").append(PK).append("=\" ").append("+ ")
                .append(PK).append(" + \" ]\";\n").append("    }\n").toString();
        final String equals = new StringBuilder("    @Override\n")
                .append("    public boolean equals(Object object) {\n")
                .append("\tif (!(object instanceof ")
                .append(className).append(")) {\n")
                .append("\t    return false;\n").append("\t}\n")
                .append("\t").append(className)
                .append(" other = (").append(className)
                .append(") object;\n").append("\tif ((this.")
                .append(PK).append(" == null && other.")
                .append(PK).append(" != null) || (this.").append(PK)
                .append(" != null && !this.").append(PK)
                .append(".equals(other.").append(PK).append("))) {\n")
                .append("\t    return false;\n").append("\t}\n")
                .append("\treturn true;\n").append("    }\n").toString();

        try {
            final String classGeneratingPath = new StringBuilder(SAVE_FILE_PATH)
                    .append(packagePathName).append(SYSTEM_SLASH)
                    .append(className).append(".java").toString();
            bw = new BufferedWriter(new PrintWriter(classGeneratingPath));
            bw.newLine();
            generateBangMapleIntroductionText(bw);
            if (HAVE_A_PACKAGE_NAME) {
                bw.newLine();
                bw.write(new StringBuilder("package ")
                        .append(txtPackageName.getText().trim()).append(";").toString());
                bw.newLine();
            }
            if (!implementsSerializable.isEmpty()) {
                bw.newLine();
                bw.write(serializable);
            }
            if (!importJavaDate.isEmpty()) {
                bw.newLine();
                bw.write(importJavaDate);
            }
            if (!implementsSerializable.isEmpty() || !importJavaDate.isEmpty()) {
                bw.newLine();
            }
            bw.newLine();
            bw.write(new StringBuilder(publicModifier).append(finalModifier)
                    .append("class ").append(className)
                    .append(implementsSerializable).append(" {").toString());
            bw.newLine();
            bw.write(new StringBuilder("\n").append(var).toString());
            bw.newLine();
            bw.write(constructor);
            bw.newLine();
            bw.write(getter);
            bw.write(setter);
            bw.newLine();

            if (!hashCode.isEmpty() && chkHashCodeEntity.isSelected()) {
                bw.write(hashCode);
                bw.newLine();
            }
            if (!equals.isEmpty() && chkEqualsEntity.isSelected()) {
                bw.write(equals);
                bw.newLine();
            }
            if (!toString.isEmpty() && chkToStringEntity.isSelected()) {
                bw.write(toString);
                bw.newLine();
            }
            bw.write("}");
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Successfully generated this java class file.");
        } catch (IOException ex) {
            errorGeneratingClassMessage();
        } finally {
            closeBufferedWriter(bw);
        }
    }

    private boolean validateClassNameDBEntity() {
        String className = txtClassNameEntity.getText();
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

    private void btnGenerateEntityDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateEntityDBActionPerformed
        if (!validateClassNameDBEntity()) {
            return;
        }
        selectProjectPath("dbentity");
    }//GEN-LAST:event_btnGenerateEntityDBActionPerformed

    private void getPropertiesFromDBForEntity() {
        Connection conn = null;
        PreparedStatement prStm = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(new StringBuilder("jdbc:sqlserver://")
                    .append(txtIP.getText()).append(":").append(txtPort.getText())
                    .append(";databaseName=").append(txtDBName.getText())
                    .append(";username=").append(txtUsername.getText())
                    .append(";password=")
                    .append(String.valueOf(txtPassword.getPassword())).toString());
            rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
            List<String> tblList = new ArrayList<>();
            while (rs.next()) {
                final String className = rs.getString("TABLE_NAME");
                tblList.add(className);
            }
            rs.close();
            txtClassNameEntity.setText(tblList.get(0));
            tblList.remove("sysdiagrams");
            tblList.remove("trace_xe_action_map");
            tblList.remove("trace_xe_event_map");

            List<String> varList = new ArrayList<>();
            List<String> dataTypeList = new ArrayList<>();
            Map<String, String> dataTypeMap = new LinkedHashMap<>();
            dataTypeMap.put("int", "Integer");
            dataTypeMap.put("date", "Date");
            dataTypeMap.put("nvarchar", "String");
            dataTypeMap.put("varchar", "String");
            dataTypeMap.put("char", "Character");
            dataTypeMap.put("bit", "Boolean");
            dataTypeMap.put("datetime", "Date");
            dataTypeMap.put("float", "Float");

            for (int j = 0; j < tblList.size(); j++) {
                prStm = conn.prepareStatement("SELECT * FROM " + tblList.get(j));
                rs = prStm.executeQuery();
                int k = 0;
                for (int l = 0; l < rs.getMetaData().getColumnCount(); l++) {
                    varList.add(rs.getMetaData().getColumnName(++k).substring(0, 1).toLowerCase() + rs.getMetaData().getColumnName(k).substring(1));
                    dataTypeList.add(rs.getMetaData().getColumnTypeName(k));
                }
                rs.close();
                rs = conn.getMetaData().getPrimaryKeys(null, null, tblList.get(j));
                rs.next();
                String pk = rs.getString("COLUMN_NAME");
                pk = pk.substring(0, 1).toLowerCase() + pk.substring(1);
                if (pk != null) {
                    chkToStringEntity.setEnabled(true);
                    chkHashCodeEntity.setEnabled(true);
                    chkEqualsEntity.setEnabled(true);
                }
                PK = pk;
                rs.close();
            }
            DefaultTableModel entityTBLModel = (DefaultTableModel) jTable3.getModel();
            entityTBLModel.setRowCount(0);
            for (int i = 0; i < varList.size(); i++) {
                entityTBLModel.addRow(new Object[]{"private", dataTypeMap.get(dataTypeList.get(i)), varList.get(i), true, true, true});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while generating Entity class from Database.");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prStm != null) {
                    prStm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error while generating Entity class from Database.");
            }
        }
    }

    private void chkCheckDBConnectionEntityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCheckDBConnectionEntityItemStateChanged
        chkPublicEntity.setEnabled(true);
        chkFinalEntity.setEnabled(true);
        chkImplSerializableEntity.setEnabled(true);
        txtClassNameEntity.setEnabled(true);
        btnGenerateEntityDB.setEnabled(true);
        jTable3.setEnabled(true);
    }//GEN-LAST:event_chkCheckDBConnectionEntityItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        initSaveDialog("projectPath");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbProjectsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProjectsItemStateChanged
        // TODO add your handling code here:
        int index = cbProjects.getSelectedIndex();
        if (index > 0) {
            String path = pPATH.get(index);
            if (path.contains("/") && SYSTEM_SLASH.equals("\\")) {
                path = path.replaceAll("/", SYSTEM_SLASH) + "\\";
            } else {
                path += SYSTEM_SLASH;
            }
            SAVE_FILE_PATH = path;

            txtProjectName.setText(path);
        }

    }//GEN-LAST:event_cbProjectsItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Properties p = new Properties();
        p.setProperty("defaultNBPP", txtDefaultNBPP.getText());
        p.setProperty("theme", String.valueOf(cbTheme.getSelectedItem()));
        p.setProperty("lang", String.valueOf(cbLang.getSelectedItem()));

        if (chkSaveSQLConnection.isSelected()) {
            p.setProperty("sqlIP", txtIP.getText());
            p.setProperty("sqlPort", txtPort.getText());
            p.setProperty("sqlUsername", txtUsername.getText());
            p.setProperty("sqlDB", txtDBName.getText());
            if (chkSavePassword.isSelected()) {
                p.setProperty("sqlPassword", String.valueOf(txtPassword.getPassword()));
            }
        }

        try {
            OutputStream os = new FileOutputStream("conf.properties");
            p.store(os, "BangMapleJDBCGenerator");
            JOptionPane.showMessageDialog(this, "Successfully saved the current configuration file!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save the configuration file.\nThe application will now exit.");
            System.exit(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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

    private boolean validateExtendingDTO() {
        final String extendsDTOText = txtExtendsDTO.getText().trim();
        if (!extendsDTOText.isEmpty()) {
            if (!String.valueOf(extendsDTOText.charAt(0)).matches("^[a-zA-Z]$")) {
                JOptionPane.showMessageDialog(this,
                        "First letter of class name or package name must be alphabet."
                        + "\nPlease try again.");
                return false;
            } else {
                if (!extendsDTOText.matches("^[a-zA-Z0-9\\_]{1,256}$")) {
                    JOptionPane.showMessageDialog(this,
                            new StringBuilder("You must follow the class naming format.")
                                    .append("\nPlease try again later.").toString());
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateImplementingDTO() {
        final String implementsDTOText = txtImplementsDTO.getText().trim();
        if (!implementsDTOText.isEmpty()) {
            if (!String.valueOf(implementsDTOText.charAt(0)).matches("^[a-zA-Z]$")) {
                JOptionPane.showMessageDialog(this,
                        new StringBuilder("First letter of class name or package name must be alphabet.")
                                .append("\nPlease try again.").toString());
                return false;
            } else {
                if (!implementsDTOText.matches("^[a-zA-Z0-9\\,\\_\\.\\s]{1,256}$")) {
                    JOptionPane.showMessageDialog(this,
                            new StringBuilder("You must follow the class naming format.")
                                    .append("\nPlease try again later.").toString());
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateIfExistExtendingOrImplementing() {
        return (validateExtendingDTO() && validateImplementingDTO());
    }

    private boolean validateDBNameConnection() {
        final String dbName = txtDBName.getText().trim();
        if (dbName.isEmpty()) {
            txtDBName.setText("master");
        }
        if (!dbName.matches("^[a-zA-Z0-9\\.\\_-]{1,254}$")) {
            JOptionPane.showMessageDialog(this, "Only accept valid character.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateUsernameConnection() {
        final String userName = txtUsername.getText().trim();
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave username blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (!userName.matches("^[a-zA-Z0-9]{1,254}$")) {
                JOptionPane.showMessageDialog(this, "Only accept valid character.",
                        "Error while generating class", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean validatePasswordConnection() {
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave password blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateIPConnection() {
        final String ip = txtIP.getText().trim();
        if (ip.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave username blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (!ip.matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")) {
                if (!ip.matches("^[a-zA-Z0-9\\.]{1,254}$")) {
                    JOptionPane.showMessageDialog(this, "Only accept valid IP Address.",
                            "Error while generating class", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validatePortConnection() {
        final String port = txtPort.getText().trim();
        if (port.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please don't leave port blank.",
                    "Error while generating class", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (!port.matches("^[0-9]{1,5}$")) {
                if (!port.matches("^[a-zA-Z0-9\\.]{1,254}$")) {
                    JOptionPane.showMessageDialog(this, "Only accept valid IP Address.",
                            "Error while generating class", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            try {
                Integer.parseInt(port);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Only accept valid port.",
                        "Error while generating class", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean validatePackageNameConnection() {
        final String packageName = txtPackageName.getText().trim();
        if (!packageName.isEmpty()) {
            if (packageName.charAt(0) == '.') {
                JOptionPane.showMessageDialog(this, new StringBuilder("Package name ")
                        .append("musn't be started with a dot. Please try again.").toString());
                return false;
            } else {
                if (!packageName.matches("^[a-zA-Z0-9\\.]{1,256}$")) {
                    JOptionPane.showMessageDialog(this, new StringBuilder("Package ")
                            .append("name is not correct! Please follow the right format.").toString());
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateConnection() {
        return validateDBNameConnection() && validateUsernameConnection()
                && validatePasswordConnection() && validateIPConnection()
                && validatePortConnection() && validatePortConnection()
                && validatePackageNameConnection();
    }

    private boolean validateClassNameJDBCUtils() {
        final String className = txtClassNameJDBC.getText().trim();
        if (className.matches("^[\\_a-zA-Z0-9]{1,255}$")) {
            if (className.substring(0, 1).matches("^[0-9]$")
                    || className.substring(0, 1).matches("^[\\.]$")) {
                JOptionPane.showMessageDialog(this,
                        new StringBuilder("Can't generate due to class name is not valid!\n")
                                .append("The first letter must not a number.").toString());
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Can't generate due to class name is not valid!");
            return false;
        }
        return true;
    }

    private boolean validateGetConnectionMethodJDBCUtils() {
        final String getConnection = txtGetConnection.getText().trim();
        if (getConnection.isEmpty()) {
            JOptionPane.showMessageDialog(this, "getConnection method name musn't be blank!");
            return false;
        } else {
            if (!getConnection.matches("^[a-zA-Z0-9]{1,}$")
                    || String.valueOf(getConnection.charAt(0)).matches("^[0-9]$")) {
                JOptionPane.showMessageDialog(this, "Please follow the correct method name format.");
                return false;
            }
        }
        return true;
    }

    private boolean validateJDBCUtilsData() {
        return validateClassNameJDBCUtils() && validateGetConnectionMethodJDBCUtils();
    }

    private boolean validateJDBCDAOClassName() {
        final String className = txtClassNameJDBCDAO.getText().trim();
        if (className.matches("^[\\_a-zA-Z0-9]{1,255}$")) {
            if (className.substring(0, 1).matches("^[0-9]$")
                    || className.substring(0, 1).matches("^[\\.]$")) {
                JOptionPane.showMessageDialog(this,
                        new StringBuilder("Can't generate due to class name is not valid!\n")
                                .append("The first letter must not a number.").toString());
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Can't generate due to class name is not valid!");
            return false;
        }
        return true;
    }

    private static void setLookAndFeel(final String mode) {
        try {
            if (mode.equals("Dark mode")) {
                javax.swing.UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
            } else {
                javax.swing.UIManager.setLookAndFeel(new MaterialLookAndFeel());
            }
        } catch (UnsupportedLookAndFeelException e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        setLookAndFeel("Dark mode");
        java.awt.EventQueue.invokeLater(() -> {
            new MainController().setVisible(true);
        });
    }

    private class MyFileChooser extends JFileChooser {

        public MyFileChooser(String path) {
            super(path);
        }

        @Override
        public JDialog createDialog(Component parent) throws HeadlessException {
            return super.createDialog(parent);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseProject;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnGenerateEntity;
    private javax.swing.JButton btnGenerateEntityDB;
    private javax.swing.JButton btnGenerateJDBCDAO;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbAddModifier;
    private javax.swing.JComboBox<String> cbCloseConnectionModifier;
    private javax.swing.JComboBox<String> cbConnectionModifier;
    private javax.swing.JComboBox<String> cbFindModifier;
    private javax.swing.JComboBox<String> cbGetAllModifier;
    private javax.swing.JComboBox<String> cbGetConnection;
    private javax.swing.JComboBox<String> cbLang;
    private javax.swing.JComboBox<String> cbPreparedStatementModifier;
    private javax.swing.JComboBox<String> cbProjects;
    private javax.swing.JComboBox<String> cbRemoveModifier;
    private javax.swing.JComboBox<String> cbResultSetModifier;
    private javax.swing.JComboBox<String> cbTheme;
    private javax.swing.JComboBox<String> cbUpdateModifier;
    private javax.swing.JCheckBox chkAddJDBCDAO;
    private javax.swing.JCheckBox chkCheckDBConnectionEntity;
    private javax.swing.JCheckBox chkCloseConnection;
    private javax.swing.JCheckBox chkConnection;
    private javax.swing.JCheckBox chkEqualsEntity;
    private javax.swing.JCheckBox chkFinal;
    private javax.swing.JCheckBox chkFinalDTO;
    private javax.swing.JCheckBox chkFinalEntity;
    private javax.swing.JCheckBox chkFinalJDBCDAO;
    private javax.swing.JCheckBox chkFindJDBCDAO;
    private javax.swing.JCheckBox chkGetterConnectionJDBCDAO;
    private javax.swing.JCheckBox chkGetterPreparedStatementJDBCDAO;
    private javax.swing.JCheckBox chkGetterResultSetJDBCDAO;
    private javax.swing.JCheckBox chkHashCodeEntity;
    private javax.swing.JCheckBox chkImplSerializable;
    private javax.swing.JCheckBox chkImplSerializableEntity;
    private javax.swing.JCheckBox chkPreparedStatement;
    private javax.swing.JCheckBox chkPublic;
    private javax.swing.JCheckBox chkPublicDTO;
    private javax.swing.JCheckBox chkPublicEntity;
    private javax.swing.JCheckBox chkPublicJDBCDAO;
    private javax.swing.JCheckBox chkRemoveJDBCDAO;
    private javax.swing.JCheckBox chkResultSet;
    private javax.swing.JCheckBox chkSavePassword;
    private javax.swing.JCheckBox chkSaveSQLConnection;
    private javax.swing.JCheckBox chkSerializable;
    private javax.swing.JCheckBox chkSerializableDTO;
    private javax.swing.JCheckBox chkSetterConnectionJDBCDAO;
    private javax.swing.JCheckBox chkSetterPreparedStatementJDBCDAO;
    private javax.swing.JCheckBox chkSetterResultSetJDBCDAO;
    private javax.swing.JCheckBox chkToStringEntity;
    private javax.swing.JCheckBox chkUpdateJDBCDAO;
    private javax.swing.JCheckBox chkgetAllJDBCDAO;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLogoJDBC;
    private javax.swing.JLabel lblMaxMin;
    private javax.swing.JLabel lblMin;
    private javax.swing.JTextField txtClassName;
    private javax.swing.JTextField txtClassNameEntity;
    private javax.swing.JTextField txtClassNameJDBC;
    private javax.swing.JTextField txtClassNameJDBCDAO;
    private javax.swing.JTextField txtDBName;
    private javax.swing.JTextField txtDefaultNBPP;
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
