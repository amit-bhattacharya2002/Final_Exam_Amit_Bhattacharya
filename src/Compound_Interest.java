import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * Created by JFormDesigner on Sat Aug 14 04:29:21 IST 2021
 */



/**
 * @author unknown
 */
public class Compound_Interest extends JFrame{


    //Connection object con instantiated to create a connection with the database
    Connection_Db con = new Connection_Db();
    Connection conObj = con.connect();
    String[] savingsType = {"Savings-Deluxe", "Savings-Regular"};   //Combo box options are created


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Compound_Interest form = new Compound_Interest();

        form.setDefaultCloseOperation(EXIT_ON_CLOSE);//So that the program stops executing when the users clicks the cross button
        form.setVisible(true);  //The form is set to be visible
    }


    //Method to update the jtable
    public void updateTable() throws SQLException{

        String quer1 = "Select * from savingstable";    //Query for the sql server

        //Getting the data from the server
        PreparedStatement query = conObj.prepareStatement(quer1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = query.executeQuery();

        DefaultTableModel df = (DefaultTableModel) tableData.getModel();

        rs.last();

        int i = rs.getRow();

        rs.beforeFirst();

        String[][] array = new String[0][1];
        if(i > 0){

            array = new String[i][5];

        }

        int j = 0;

        //Putting the data in the server

        while (rs.next()){
            array[j][0] = rs.getString("custno");
            array[j][1] = rs.getString("custname");
            array[j][2] = rs.getString("cdep");
            array[j][3] = rs.getString("nyears");
            array[j][4] = rs.getString("savtype");
        }

        //Putting the data in the table
        String[] columns = {"Number", "Name", "Deposit", "Years", "Type of Savings"};
        DefaultTableModel model = new DefaultTableModel(array, columns);
        tableData.setModel(model);
    }


    public Compound_Interest() throws SQLException, ClassNotFoundException {
        initComponents();
    }

    private void btnAddActionPerformed(ActionEvent e) throws SQLException {

        //Declaring local variable to store the values in the interim


        String name, num, sav;
        double dep=0;
        int years=0;

        name = txtName.getText();
        num = txtNum.getText();
        sav = cboxSavings.getSelectedItem().toString();

        //Validations for numeric values
        try{
            Integer.parseInt(num);
        }catch(Exception E){
            JOptionPane.showMessageDialog(null, "You must enter a numeric value");
        }
        //Validations for numeric values
        try{
            dep = Double.parseDouble(txtDep.getText());
        }catch(Exception E){
            JOptionPane.showMessageDialog(null, "You must enter a numeric value");
        }
        //Validation for numeric values
        try{
            years = Integer.parseInt(txtYears.getText());
        }catch(Exception E){
            JOptionPane.showMessageDialog(null, "You must enter a numeric value");
        }

        Savings ob = new Savings(name, num, sav, dep, years);
        String query1 = "Select * from savingstable where custno = ?";
        PreparedStatement query = conObj.prepareStatement(query1);

        query.setString(1,num);

        ResultSet rs = query.executeQuery();

        if(rs.isBeforeFirst()){
            JOptionPane.showMessageDialog(null,"This record is existing");
            return;
        }
        //Adding the data to the database
        String query2 = "Insert into savingstable values (?,?,?,?,?)";
        query = conObj.prepareStatement(query2);

        query.setString(1,ob.number);
        query.setString(2,ob.name);
        query.setDouble(3,ob.deposit);
        query.setInt(4,ob.years);
        query.setString(5,ob.savingstype);

        query.executeUpdate();

        JOptionPane.showMessageDialog(null, "One Record Added");

        updateTable();
        //Setting the values back to empty
        txtName.setText("");
        txtNum.setText("");
        txtDep.setText("");
        txtYears.setText("");

    }

    
    
    private void btnEditActionPerformed(ActionEvent e) throws SQLException {
        DefaultTableModel df = (DefaultTableModel) tableData.getModel();

        int index = tableData.getSelectedRow();

        String name, num, sav;
        double dep;
        int years;

        name = txtName.getText();
        num = txtNum.getText();
        dep = Double.parseDouble(txtDep.getText());
        years = Integer.parseInt(txtYears.getText());
        sav = cboxSavings.getSelectedItem().toString();

        //Getting old value index from the table
        String oldvalue = df.getValueAt(index, 0).toString();

        String query1 = "Update savingstable set custno=?, custname=?, cdep=?, nyears=?, savtype=? where custno =?";
        PreparedStatement query = conObj.prepareStatement(query1);

        //Updating the database with the new values
        query.setString(1,num);
        query.setString(2,name);
        query.setDouble(3, dep);
        query.setInt(4, years);
        query.setString(5,sav);
        query.setString(6,oldvalue);

        query.executeUpdate();

        updateTable();
        //Setting the values back to empty
        txtName.setText("");
        txtNum.setText("");
        txtDep.setText("");
        txtYears.setText("");

    }

    private void btnDelActionPerformed(ActionEvent e) throws SQLException {

        String name, num, sav;
        double dep;
        int years;

        name = txtName.getText();
        num = txtNum.getText();
        dep = Double.parseDouble(txtDep.getText());
        years = Integer.parseInt(txtYears.getText());
        sav = cboxSavings.getSelectedItem().toString();

        //Deleting the data from the database
        String query = "Delete from savingstable where custno =?";
        PreparedStatement query2 = conObj.prepareStatement(query);

        query2.setString(1,num);


        query2.executeUpdate();

        updateTable();
        //Setting the text fields back to empty
        txtName.setText("");
        txtNum.setText("");
        txtDep.setText("");
        txtYears.setText("");
    }

    private void cboxSavingsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        txtNum = new JTextField();
        label2 = new JLabel();
        txtName = new JTextField();
        label3 = new JLabel();
        txtDep = new JTextField();
        label4 = new JLabel();
        txtYears = new JTextField();
        label5 = new JLabel();
        cboxSavings = new JComboBox(savingsType);
        scrollPane1 = new JScrollPane();
        tableData = new JTable();
        scrollPane2 = new JScrollPane();
        tableCalculated = new JTable();
        panel1 = new JPanel();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDel = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Enter the Customer Number :");
        contentPane.add(label1, "cell 0 0");

        //---- txtNum ----
        txtNum.setColumns(100);
        contentPane.add(txtNum, "cell 7 0");

        //---- label2 ----
        label2.setText("Enter the Customer Name :");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(txtName, "cell 7 1");

        //---- label3 ----
        label3.setText("Enter the Initial Deposit :");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(txtDep, "cell 7 2");

        //---- label4 ----
        label4.setText("Enter the number of years :");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(txtYears, "cell 7 3");

        //---- label5 ----
        label5.setText("Choose the type of savings :");
        contentPane.add(label5, "cell 0 4");

        //---- cboxSavings ----
        cboxSavings.addActionListener(e -> cboxSavingsActionPerformed(e));
        contentPane.add(cboxSavings, "cell 7 4");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tableData);
        }
        contentPane.add(scrollPane1, "cell 0 5 8 1");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(tableCalculated);
        }
        contentPane.add(scrollPane2, "cell 0 5 8 1");

        //======== panel1 ========
        {
            panel1.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(
            0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder
            .BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.
            red),panel1. getBorder()));panel1. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.
            beans.PropertyChangeEvent e){if("bord\u0065r".equals(e.getPropertyName()))throw new RuntimeException();}});
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- btnAdd ----
            btnAdd.setText("ADD");
            btnAdd.addActionListener(e -> {
                try {
                    btnAddActionPerformed(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            panel1.add(btnAdd, "cell 0 2 7 1");

            //---- btnEdit ----
            btnEdit.setText("EDIT");
            btnEdit.addActionListener(e -> {
                try {
                    btnEditActionPerformed(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            panel1.add(btnEdit, "cell 7 2 7 1");

            //---- btnDel ----
            btnDel.setText("DELETE");
            btnDel.addActionListener(e -> {
                try {
                    btnDelActionPerformed(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            panel1.add(btnDel, "cell 14 2 7 1");
        }
        contentPane.add(panel1, "cell 0 8 8 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label1;
    private JTextField txtNum;
    private JLabel label2;
    private JTextField txtName;
    private JLabel label3;
    private JTextField txtDep;
    private JLabel label4;
    private JTextField txtYears;
    private JLabel label5;
    private JComboBox cboxSavings;
    private JScrollPane scrollPane1;
    private JTable tableData;
    private JScrollPane scrollPane2;
    private JTable tableCalculated;
    private JPanel panel1;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
