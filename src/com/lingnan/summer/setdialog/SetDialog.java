package com.lingnan.summer.setdialog;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;








import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.domain.Task;
import com.lingnan.summer.service.TaskService;
import com.lingnan.summer.service.impl.TaskServiceImpl;
import com.lingnan.summer.view.AlarmView;


public class SetDialog extends JDialog implements ActionListener {

	   private JPanel idPanel,namePanel,measurePanel,opePanel,modelPanel;
		
		private JLabel idLabel,nameLabel,measureLabel,modelLabel;
		private JTextField idTF,nameTF,measureTF,modelTF,tempTF;//tempTF温度
		
		private JButton saveBtn,cancelBtn,addBtn,subBtn;
		
	//	private AlarmService alarmService = new AlarmServiceImpl();
		private TaskService taskService = new TaskServiceImpl();
		
		private AlarmView alarmView;
		
		private Alarm alarm;
		private Task task;
		
		
		//设置
		private JLabel addLabel,subLabel;
		

		public SetDialog(JFrame parent,AlarmView alarmView) {
			super(parent,"设置");
			this.alarmView = alarmView;
			
			setSize(350,300);
			
			setLocationRelativeTo(null);
			
			setModal(true);
			setResizable(false);
			 
			this.setLayout(new FlowLayout());
			
			initView();
		}
		
		public SetDialog(JFrame parent,AlarmView alarmView,Alarm alarm) {
			 this(parent,alarmView);
			 this.alarm = alarm;
			 setTitle("设置");
			 //回显
			 this.nameTF.setText(alarm.getDname());
			 
		 //设置模式
		// 	 this.modelTF.setText(task.getMeasure()+"");
			 	 
			 this.idPanel.setVisible(false);
		}
		
		private void initView() {
			idPanel = new JPanel();
			idLabel = new JLabel("id");
			idTF = new JTextField(15);
			idPanel.add(idLabel);
			idPanel.add(idTF);
			
			namePanel = new JPanel();
			nameLabel = new JLabel("设备名称");
			nameTF = new JTextField(15);
			namePanel.add(nameLabel);
			namePanel.add(nameTF);
			
			measurePanel = new JPanel();
			measureLabel = new JLabel("频道");
			measurePanel.add(measureLabel);
			measureTF = new JTextField("0", 5);
			measurePanel.add(measureTF);
						
			ImageIcon addicon = new ImageIcon("static\\icon\\增加.png");
			addicon.setImage(addicon.getImage().getScaledInstance(20, 20,
					Image.SCALE_DEFAULT));
			addBtn = new JButton(addicon);
			addBtn.setContentAreaFilled(false);//透明
			
			ImageIcon subicon = new ImageIcon("static\\icon\\减小.png");
			subicon.setImage(subicon.getImage().getScaledInstance(20, 20,
					Image.SCALE_DEFAULT));
			subBtn = new JButton( subicon);
			subBtn.setContentAreaFilled(false);//透明
			
			
			addBtn.addActionListener(this);
			subBtn.addActionListener(this);
			measurePanel.add(addBtn);
			measurePanel.add(subBtn);
		

			
//设置模式
			modelPanel = new JPanel();
			modelLabel = new JLabel("措施");
			modelTF = new JTextField(15);
			modelPanel.add(modelLabel);
	        modelPanel.add(modelTF);
			
			
			opePanel = new JPanel();
			saveBtn = new JButton("保存");
			cancelBtn = new JButton("取消");
			saveBtn.addActionListener(this);
			cancelBtn.addActionListener(this);
			opePanel.add(saveBtn);
			opePanel.add(cancelBtn);
			
			Container container = getContentPane();
			container.add(idPanel);
			container.add(namePanel);
			container.add(measurePanel);
			container.add(modelPanel);
			container.add(opePanel);
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		int temp = Integer.parseInt(measureTF.getText()) ;
		measureTF.setText(temp+"");
		if (source == addBtn) {
			// 频道增加
			temp = temp + 1;
			measureTF.setText(temp + "");
		} else if (source == subBtn) {
			// 频道减小
			temp = temp - 1;
			measureTF.setText(temp + "");
		} else if (source == saveBtn) {
			String name = nameTF.getText();
			String measure = measureTF.getText();

			if (this.task == null) {
				// TODO 参数校验
				Alarm alarm = new Alarm();
				alarm.setDname(name);
				alarm.setTemp(temp+"");
				alarm.setMeasure(measure);
				Task task = new Task();
				task.setDname(name);
				task.setInstruction(name+temp+""+measure);	
				
				int result = taskService.add(task);
				if (result == 1) {
					this.dispose();
					JOptionPane.showMessageDialog(this, "设置成功", "提示",JOptionPane.INFORMATION_MESSAGE);
					// 刷新
					alarmView.refresh();

				} else {
					JOptionPane.showMessageDialog(this, "设置失败", "提示",JOptionPane.ERROR_MESSAGE);
				}
			}

		}else if (source == cancelBtn) {
				this.dispose();
			}
		}

	}




