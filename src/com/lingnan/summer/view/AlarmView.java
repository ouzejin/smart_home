package com.lingnan.summer.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.lingnan.summer.dialog.AlarmDialog;
import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.AlarmQuery;
import com.lingnan.summer.service.AlarmService;
import com.lingnan.summer.service.impl.AlarmServiceImpl;
import com.lingnan.summer.setdialog.SetDialog;
import com.lingnan.summer.table.AlarmTableModel;
import com.lingnan.summer.view.base.BaseView;


public class AlarmView extends BaseView implements ActionListener{

	//����
	private JPanel toolBarPanel;
	
	private JPanel searchPanel;
	private JLabel nameLabel;
	private JTextField nameSearchTF;
	private JButton searchBtn;
	
	private JPanel opePanel;
	private JButton updateBtn,deleteBtn;
	
	private JButton controlBtn;   //����  
	
	//�м�
	private JScrollPane tableScrollPane;
	private JTable alarmTable;
	
	//����
	private JPanel bottomPanel;
	private JLabel countInfoLabel;
	
	
	private AlarmTableModel alarmTableModel;
	
	private JFrame jFrame;
	
	private AlarmService alarmService=new AlarmServiceImpl();
	
	public AlarmView(){
		super(1000,700,"�豸����");
	}
	
	@Override
	protected void initView() {
        toolBarPanel = new JPanel(new BorderLayout()); 
		
		searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		nameLabel = new JLabel("�豸��");
		nameSearchTF = new JTextField(10);
		
		ImageIcon serchicon = new ImageIcon("static\\icon\\����.png");
		serchicon.setImage(serchicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		searchBtn = new JButton("����", serchicon);
		searchBtn.addActionListener(this);
		
		
		opePanel =  new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		ImageIcon updateicon = new ImageIcon("static\\icon\\�޸�.png");
		updateicon.setImage(updateicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		updateBtn = new JButton("����", updateicon);
		ImageIcon deleteicon = new ImageIcon("static\\icon\\ɾ��.png");
		deleteicon.setImage(deleteicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		deleteBtn = new JButton("ɾ��", deleteicon);
		
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		
		opePanel.add(updateBtn);
		opePanel.add(deleteBtn);
		
		// ����
		ImageIcon controlicon = new ImageIcon("static\\icon\\����.png");
		controlicon.setImage(controlicon.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		controlBtn = new JButton("����", controlicon);
		controlBtn.addActionListener(this);
		opePanel.add(controlBtn);
		 
		searchPanel.add(nameLabel);
		searchPanel.add(nameSearchTF);
		searchPanel.add(searchBtn);
		
		
		toolBarPanel.add(searchPanel,"West");
		toolBarPanel.add(opePanel,"East");
		
		//�м�
		alarmTable = new JTable();
		tableScrollPane = new JScrollPane(alarmTable);
		
		//����
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		countInfoLabel = new JLabel();
		bottomPanel.add(countInfoLabel);
		
		this.add(toolBarPanel,"North");
		this.add(tableScrollPane,"Center");
		this.add(bottomPanel,"South");
		
		setVisible(true);
		
		refresh();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(updateBtn==source) {
			int rowIndex = alarmTable.getSelectedRow();
			if(rowIndex==-1) {
				JOptionPane.showMessageDialog(this,"��ѡ��һ��");
				return;
			}
			int id = (Integer)alarmTable.getValueAt(rowIndex,0);
			Alarm alarm = alarmService.findAlarmById(id);
			
			if(alarm==null) {
				JOptionPane.showMessageDialog(this,"���ݲ�����,��ˢ��");
				return;
			}
			
			AlarmDialog alarmDialog = new AlarmDialog(jFrame,this,alarm);
			alarmDialog.setVisible(true);
		}else if(deleteBtn==source) {
			//��ȡѡ�м�¼
			int rowIndex = alarmTable.getSelectedRow();
			if(rowIndex==-1) {
				JOptionPane.showMessageDialog(this,"��ѡ��һ��");
				return;
			}
			int id = (Integer)alarmTable.getValueAt(rowIndex,0);
			if(alarmService.deleteById(id)==1) {
				JOptionPane.showMessageDialog(this,"ɾ���ɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
			    refresh();
			}else {
				JOptionPane.showMessageDialog(this,"ɾ��ʧ��","��ʾ",JOptionPane.ERROR_MESSAGE);
			}
		}//����
		else if(controlBtn==source) {
			int rowIndex = alarmTable.getSelectedRow();
			if(rowIndex==-1){
				JOptionPane.showMessageDialog(this, "��ѡ������һ����¼!");
				return;
			}
			int id = (Integer)alarmTable.getValueAt(rowIndex,0);
			Alarm alarm = alarmService.findAlarmById(id);
			if(alarm==null){
				JOptionPane.showMessageDialog(this,"��¼�����ڣ���ˢ��!");
				return;
			}
			SetDialog setDialog = new SetDialog(jFrame,this,alarm);
			setDialog.setVisible(true);
	}
		else if(searchBtn==source) {
			refresh();
		}
	}
	
	public void refresh() {
		String name = nameSearchTF.getText();

		AlarmQuery alarmQuery = new AlarmQuery();
		alarmQuery.setDname(name);

		AlarmTableModel alarmTableModel = new AlarmTableModel();
		alarmTableModel.query(alarmQuery);

		Page<Alarm> page = alarmTableModel.getPage();
		countInfoLabel.setText("�ܹ�" + page.getCount() + "����¼");

		alarmTable.setModel(alarmTableModel);
	}
}
