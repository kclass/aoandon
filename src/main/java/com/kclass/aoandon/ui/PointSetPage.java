package com.kclass.aoandon.ui;

import com.kclass.aoandon.component.MyPlainButton;
import com.kclass.aoandon.constant.Constant;
import com.kclass.aoandon.util.AoandonUtil;
import com.kclass.aoandon.vo.PointDetailInfo;
import com.kclass.aoandon.vo.PointInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @author hu.kai
 * @description 取点页面
 * @employeeId 10102
 * @date 2022/3/24 14:16
 */
public abstract class PointSetPage extends JPanel {
    /**
     * 点列表
     */
    private Map<String, List<PointDetailInfo>> pointListMap;

    /**
     * 组下拉选
     */
    private JComboBox<String> groupComboBox;

    /**
     * 点下拉选
     */
    private JComboBox<PointDetailInfo> pointComboBox;

    /**
     * 下方信息展示标签
     */
    private JLabel infoLabel;

    public PointSetPage(Map<String, List<PointDetailInfo>> pointListMap) {
        super(true);
        if (Objects.isNull(pointListMap)) {
            pointListMap = new HashMap<>();
        }
        this.pointListMap = pointListMap;
        init();
    }

    private void init() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        //返回按钮
        this.addBackPanel();

        //添加内容
        this.addContent();

        //为了让内容居中
        JPanel eastBlankPanel = new JPanel();
        eastBlankPanel.setBackground(Color.WHITE);
        eastBlankPanel.setPreferredSize(new Dimension(50, 30));
        this.add(eastBlankPanel, BorderLayout.EAST);
    }

    private void addContent() {
        JPanel outPanel = new JPanel(new GridLayout(2, 1));
        outPanel.setBackground(Color.WHITE);
        outPanel.add(createUpPanel());
        outPanel.add(createDownPanel());
        this.add(outPanel);
    }

    /**
     * 下面的内容
     *
     * @return 下面
     */
    private JPanel createDownPanel() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        jPanel.setBackground(Color.WHITE);
        this.add(jPanel);
        this.infoLabel = new JLabel();
        this.infoLabel.setFont(Constant.FONT_RADIO);
        PointDetailInfo pointDetailInfo = ((PointDetailInfo) this.pointComboBox.getSelectedItem());
        if (Objects.nonNull(pointDetailInfo)) {
            this.infoLabel.setText(pointDetailInfo.getDesc());
        }

        jPanel.add(this.infoLabel);
        return jPanel;
    }

    /**
     * 上面的内容
     *
     * @return 上面
     */
    private JPanel createUpPanel() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        jPanel.setBackground(Color.WHITE);
        this.add(jPanel);

        //按钮选择器
        createGroupSelect();
        jPanel.add(groupComboBox);
        createPointSelect();
        jPanel.add(pointComboBox);

        //添加点
        jPanel.add(this.addPointAddButton());
        return jPanel;
    }

    /**
     * 新增点
     *
     * @return 按钮
     */
    private JButton addPointAddButton() {
        JButton jButton = new MyPlainButton("添加点");
        jButton.addActionListener(e -> {
            int confirm = 1;
            while (confirm == 1) {
                JOptionPane.showMessageDialog(null, "请选择点");
                AoandonUtil.ROBOT.delay(0000);
                PointInfo pointInfo = AoandonUtil.pointInfo();
                confirm = JOptionPane.showConfirmDialog(null, "确认添加吗");
                if (confirm == 0) {
                    JOptionPane.showMessageDialog(createPointDetailInput(), "请添加详细信息");
                }
            }
        });
        return jButton;
    }

    private JPanel createPointDetailInput() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //分组选择
        JComboBox<String> jComboBox = new JComboBox<>();
        jComboBox.addItem("MuMu探索");
        jComboBox.addItem("探索");
        jComboBox.addItem("魂土");
        jComboBox.addItem("双人魂土");
        jPanel.add(jComboBox);

        //名称
        JTextField nameTextField = new JTextField("请输入按钮名称");
        jPanel.add(nameTextField);

        //详细描述
        JTextField descTextField = new JTextField("请输入按钮描述");
        jPanel.add(descTextField);

        return jPanel;
    }

    /**
     * 创建组选择器
     */
    private void createPointSelect() {
        this.pointComboBox = new JComboBox<>();
        this.pointComboBox.setFont(Constant.FONT_RADIO);
        this.pointComboBox.addActionListener(e -> {
            if (Objects.nonNull(this.infoLabel)) {
                PointDetailInfo pointDetailInfo = ((PointDetailInfo) pointComboBox.getSelectedItem());
                if (Objects.nonNull(pointDetailInfo)) {
                    this.infoLabel.setText(pointDetailInfo.getDesc());
                }
            }
        });
        this.addPointSelect();
    }

    /**
     * 添加点选择器信息
     */
    private void addPointSelect() {
        if (Objects.isNull(pointComboBox)) {
            return;
        }
        this.pointComboBox.removeAllItems();
        String groupName = (String) this.groupComboBox.getSelectedItem();
        List<PointDetailInfo> pointDetailInfos = pointListMap.get(groupName);
        if (Objects.isNull(pointDetailInfos)) {
            pointDetailInfos = new ArrayList<>();
        }
        for (PointDetailInfo pointDetailInfo : pointDetailInfos) {
            this.pointComboBox.addItem(pointDetailInfo);
        }
    }

    /**
     * 创建组选择器
     */
    private void createGroupSelect() {
        JComboBox<String> jComboBox = new JComboBox<>();
        jComboBox.setFont(Constant.FONT_RADIO);
        jComboBox.addActionListener(e -> this.addPointSelect());
        for (String name : pointListMap.keySet()) {
            jComboBox.addItem(name);
        }
        this.groupComboBox = jComboBox;
    }

    /**
     * 添加返回按钮
     */
    private void addBackPanel() {
        JButton backButton = new MyPlainButton("返回");
        backButton.addActionListener(e -> this.backPage());

        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(50, 30));
        backPanel.setBackground(Color.WHITE);
        backPanel.add(backButton);
        this.add(backPanel, BorderLayout.WEST);
    }

    /**
     * 返回上一页的方法
     */
    public abstract void backPage();
}
