package com.kclass.aoandon.ui;

import com.kclass.aoandon.component.MyCombineCheckBox;
import com.kclass.aoandon.component.MyPlainButton;
import com.kclass.aoandon.component.MyTextFiled;
import com.kclass.aoandon.constant.Constant;
import com.kclass.aoandon.constant.PointDetailInfos;
import com.kclass.aoandon.util.AoandonUtil;
import com.kclass.aoandon.vo.PointDetailInfo;
import com.kclass.aoandon.vo.PointInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    public PointSetPage() {
        super(true);
        if (Objects.isNull(pointListMap)) {
            pointListMap = new HashMap<>();
        }
        this.pointListMap = PointDetailInfos.pointListMap;
        init();
    }

    private void init() {
        this.setBackground(Constant.BG_COLOR);
        this.setLayout(new BorderLayout());
        //分组选择器
        this.add(getGroupSelect(), BorderLayout.NORTH);
        //点展示区域
        this.add(getPointContent(), BorderLayout.CENTER);
        //提示区域
        //TODO
    }

    /**
     * 分组展示
     *
     * @return 分组选择组件
     */
    private JComponent getGroupSelect() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setBackground(Constant.BG_COLOR);

        //分组展示
        JPanel showPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        showPanel.setBackground(Constant.BG_COLOR);
        createGroupSelect();
        Label label = new Label("分组:");
        label.setFont(Constant.FONT_RADIO);
        showPanel.add(label);
        showPanel.add(this.groupComboBox);
        panel.add(showPanel);

        //添加分组
        JPanel managePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        managePanel.setBackground(Constant.BG_COLOR);
        JButton addButton = new MyPlainButton("新增");
        addButton.addActionListener(e -> {
            String groupName = JOptionPane.showInputDialog("请输入分组名称：");
            try {
                PointDetailInfos.addGroup(groupName);
                this.groupComboBox.addItem(groupName);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE);
            }
        });
        managePanel.add(addButton);

        //修改分组
        JButton updateButton = new MyPlainButton("修改");
        updateButton.addActionListener(e -> {
            String selectedItem = (String) this.groupComboBox.getSelectedItem();
            String groupName = JOptionPane.showInputDialog("请输入分组名称：", selectedItem);
            try {
                PointDetailInfos.updateGroup(selectedItem, groupName);
                this.groupComboBox.removeAllItems();
                for (String s : pointListMap.keySet()) {
                    this.groupComboBox.addItem(s);
                }
                this.groupComboBox.setSelectedItem(groupName);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "修改失败", JOptionPane.ERROR_MESSAGE);
            }
        });
        managePanel.add(updateButton);
        panel.add(managePanel);

        return panel;
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
     * 点展示区域
     *
     * @return 点展示区域
     */
    private JPanel getPointContent() {
        JPanel outPanel = new JPanel(new GridLayout(1, 2));
        outPanel.setBackground(Constant.BG_COLOR);
        outPanel.add(createLeftPanel());
        outPanel.add(createDownPanel());
        return outPanel;
    }

    /**
     * 下面的内容
     *
     * @return 下面
     */
    private JPanel createDownPanel() {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 50, 10));
        jPanel.setBackground(Constant.BG_COLOR);
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
     * 左侧的内容
     *
     * @return 上面
     */
    private JPanel createLeftPanel() {
        CardLayout cardLayout = new CardLayout();
        JPanel outPanel = new JPanel(cardLayout);

        //点信息页面
        createPointSelect();
        JButton pointAddButton = this.addPointAddButton(outPanel, cardLayout);
        JPanel pointInfoPanel = AoandonUtil.createVerticalPanel(pointComboBox,
                pointAddButton);
        pointInfoPanel.setBackground(Constant.BG_COLOR);
        outPanel.add(pointInfoPanel, "点信息");
        cardLayout.show(outPanel, "点信息");

        return outPanel;
    }

    /**
     * 新增点配置页面
     *
     * @param parentContainer 父容器
     * @param cardLayout      布局管理器
     * @return 点配置页面
     */
    private JPanel getAddPointSetPage(Container parentContainer, CardLayout cardLayout) {
        //分组选择
        MyCombineCheckBox myCombineCheckBox = new MyCombineCheckBox("分组", pointListMap.keySet(), (String) groupComboBox.getSelectedItem());
        //名称输入
        MyTextFiled namePanel = new MyTextFiled("点名称", null) {
            @Override
            protected void onTextFocusLost() {
                String inputTxt = this.getInputTxt();
                if (inputTxt == null || inputTxt.isEmpty()) {
                    this.setTextFiledStatus(false, "必填");
                } else if (PointDetailInfos.checkPointNameValid(inputTxt)) {
                    this.setTextFiledStatus(true, "可用");
                } else {
                    this.setTextFiledStatus(false, "重复");
                }
            }
        };
        //按钮描述
        MyTextFiled descPanel = new MyTextFiled("描述信息", null) {
            @Override
            protected void onTextFocusLost() {
                if (this.getInputTxt() != null && !this.getInputTxt().isEmpty()) {
                    this.setTextFiledStatus(true, "ok");
                } else {
                    this.setTextFiledStatus(false, "必填");
                }
            }
        };
        //点位选取
        JButton pointSelect = new MyPlainButton("点位选取");
        AtomicReference<PointInfo> pointInfo = new AtomicReference<>();
        pointSelect.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "将鼠标置于选取的点上");
            int confirm = 1;
            while (confirm == 1) {
                AoandonUtil.ROBOT.delay(3000);
                PointInfo selectPoint = AoandonUtil.pointInfo();
                confirm = JOptionPane.showConfirmDialog(null, "确认选择这个点吗");
                System.out.println(confirm);
                if (confirm == 0) {
                    pointInfo.set(selectPoint);
                }
            }
        });
        //确认
        JButton saveButton = new MyPlainButton("保存");
        saveButton.addActionListener(e -> {
            PointDetailInfo pointDetailInfo = new PointDetailInfo();
            pointDetailInfo.setGroup(AoandonUtil.appendStr(Constant.GROUP_SPLIT, myCombineCheckBox.getCheckedItem()));
            pointDetailInfo.setDesc(descPanel.getInputTxt());
            pointDetailInfo.setName(namePanel.getInputTxt());
            PointInfo pointInfo1 = pointInfo.get();
            if (Objects.nonNull(pointInfo1)) {
                pointDetailInfo.setColor(pointInfo1.getColor());
                pointDetailInfo.setPoint(pointInfo1.getPoint());
            }
            try {
                PointDetailInfos.addPoint(pointDetailInfo);
                cardLayout.show(parentContainer, "点信息");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE);
            }
        });
        //取消
        JButton cancelButton = new MyPlainButton("取消");
        cancelButton.addActionListener(e -> {
            cardLayout.show(parentContainer, "点信息");
        });
        JPanel confirmPanel = new JPanel(new GridLayout(1, 2));
        confirmPanel.setBackground(Constant.BG_COLOR);
        confirmPanel.add(saveButton);
        confirmPanel.add(cancelButton);

        //创建外层容器
        JPanel outPanel = new JPanel(new GridLayout(5, 1));
        outPanel.setBackground(Constant.BG_COLOR);
        outPanel.add(myCombineCheckBox);
        outPanel.add(namePanel);
        outPanel.add(descPanel);
        outPanel.add(pointSelect);
        outPanel.add(confirmPanel);

        return outPanel;
    }

    /**
     * 新增点
     *
     * @param outPanel   外层容器
     * @param cardLayout 布局管理器
     * @return 按钮
     */
    private JButton addPointAddButton(JPanel outPanel, CardLayout cardLayout) {
        JButton jButton = new MyPlainButton("添加点");
        jButton.addActionListener(e -> {
            JPanel pointSetPage = getAddPointSetPage(outPanel, cardLayout);
            outPanel.add(pointSetPage, "添加点");
            cardLayout.show(outPanel, "添加点");
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
     * 添加返回按钮
     */
    private void addBackPanel() {
        JButton backButton = new MyPlainButton("<<");
        backButton.addActionListener(e -> this.backPage());

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.setPreferredSize(new Dimension(50, 30));
        backPanel.setBackground(Constant.BG_COLOR);
        backPanel.add(backButton, BorderLayout.WEST);
        this.add(backPanel, BorderLayout.NORTH);
    }

    /**
     * 返回上一页的方法
     */
    public abstract void backPage();
}
