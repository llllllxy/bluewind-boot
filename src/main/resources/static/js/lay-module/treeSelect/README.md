# LayUI拓展组件：TreeSelect树形下拉选择器

#### 项目介绍
基于 layui和ztree的树形下拉选择器。支持异步加载，提供点击回调和加载完成后的回调，支持搜索、占位符修改、手动选中节点以及刷新树结构，更多功能参见使用文档。
使用文档：https://wujiawei0926.gitee.io/treeselect/docs/doc.html

#### 效果图

![输入图片说明](https://images.gitee.com/uploads/images/2018/1005/132035_bdc508ae_1157021.gif "ts1.gif")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1005/132045_2116c60f_1157021.gif "ts2.gif")

#### 使用示例

```
<input type="text" id="tree" lay-filter="tree" class="layui-input">

<script>
    layui.use(['treeSelect','form'], function () {
        var treeSelect= layui.treeSelect;

        treeSelect.render({
            // 选择器
            elem: '#tree',
            // 数据
            data: 'data/data3.json',
            // 异步加载方式：get/post，默认get
            type: 'get',
            // 占位符
            placeholder: '修改默认提示信息',
            // 是否开启搜索功能：true/false，默认false
            search: true,
            // 点击回调
            click: function(d){
                console.log(d);
            },
            // 加载完成后的回调函数
            success: function (d) {
                console.log(d);
//                选中节点，根据id筛选
//                treeSelect.checkNode('tree', 3);

//                获取zTree对象，可以调用zTree方法
//                var treeObj = treeSelect.zTree('tree');
//                console.log(treeObj);

//                刷新树结构
//                treeSelect.refresh();
            }
        });
    });
</script>
```

#### 提示
- 返回数据中的open属性用来判断是否自动展开节点
- 返回数据格式与ztree要求的格式相同

#### 数据格式参考
```
[
    {
      "id": 1,
      "name": "zzz",
      "open": true,
      "children": [
        {
          "id": 2,
          "name": "1",
          "open": false,
          "checked": true
        },
        {
          "id": 3,
          "name": "2",
          "open": false,
          "checked": true

        },
        {
          "id": 17,
          "name": "3z",
          "open": false,
          "checked": true
        }
      ],
      "checked": true
    },
    {
      "id": 4,
      "name": "评论",
      "open": false,
      "children": [
        {
          "id": 5,
          "name": "留言列表",
          "open": false,
          "checked": false
        },
        {
          "id": 6,
          "name": "发表留言",
          "open": false,
          "checked": false
        },
        {
          "id": 333,
          "name": "233333",
          "open": false,
          "checked": false
        }
      ],
      "checked": false
    },
    {
      "id": 10,
      "name": "权限管理",
      "open": false,
      "children": [
        {
          "id": 8,
          "name": "用户列表",
          "open": false,
          "children": [
            {
              "id": 40,
              "name": "添加用户",
              "open": false,

              "url": null,
              "title": "40",
              "checked": false,
              "level": 2,
              "check_Child_State": 0,
              "check_Focus": false,
              "checkedOld": false,
              "dropInner": false,
              "drag": false,
              "parent": false
            },
            {
              "id": 41,
              "name": "编辑用户",
              "open": false,
              "checked": false
            },
            {
              "id": 42,
              "name": "删除用户",
              "open": false,
              "checked": false
            }
          ],
          "checked": false
        },
        {
          "id": 11,
          "name": "角色列表",
          "open": false,
          "checked": false
        },
        {
          "id": 13,
          "name": "所有权限",
          "open": false,
          "children": [
            {
              "id": 34,
              "name": "添加权限",
              "open": false,
              "checked": false
            },
            {
              "id": 37,
              "name": "编辑权限",
              "open": false,
              "checked": false
            },
            {
              "id": 38,
              "name": "删除权限",
              "open": false,
              "checked": false
            }
          ],
          "checked": false
        },
        {
          "id": 15,
          "name": "操作日志",
          "open": false,
          "checked": false
        }
      ],
      "checked": false
    }
  ]
```