# 斗兽棋（Animal Chess）

一个用 **Java Swing** 手写的桌面版斗兽棋。

纯手工代码，**零第三方依赖**，不需要 Maven / Gradle，也不需要连网下载任何东西——用 IDEA 打开就能运行。

> 这个项目的目的不只是"能玩"，更希望它是一份**能读得懂的入门代码**：分层清晰、命名统一、每一步都有注释。如果你正在学 Java 或 Swing，可以从下面的 [代码导读](#代码导读一次点击发生了什么) 开始。

![image-20261123170824134](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20261123170824134.png)

- Gitee：https://gitee.com/coffee-style/animal_chess
- GitHub：https://github.com/MyCafe/animal_chess

---

## 目录

- [功能特性](#功能特性)
- [环境要求](#环境要求)
- [快速开始](#快速开始)
- [游戏规则](#游戏规则)
- [项目结构](#项目结构)
- [代码导读：一次点击发生了什么](#代码导读一次点击发生了什么)
- [几个值得一说的设计](#几个值得一说的设计)
- [已知不足与待办](#已知不足与待办)
- [反馈与交流](#反馈与交流)
- [开源协议](#开源协议)

---

## 功能特性

- 标准 9 行 × 7 列斗兽棋盘面
- 完整的 8 种棋子、16 枚棋子的初始布局
- 全部官方规则实现：
  - 战力等级吃子（象 > 狮 > 虎 > 豹 > 狼 > 狗 > 猫 > 鼠）
  - **鼠可吃象**（特例规则）
  - 只有老鼠能下水，且**禁止水陆之间吃子**
  - **狮、虎可以横竖跳河**
  - **河中若有老鼠，狮虎不可跳河**
  - 陷阱：敌方棋子进入后战力归零，走出后恢复
  - 不能进入自己的兽穴；进入对方兽穴即获胜
  - 吃光对方全部棋子同样获胜
- 选中棋子蓝色高亮
- 胜利弹窗 + 一键重开
- 菜单栏：开始 / 重新开始、退出

---

## 环境要求

| 项 | 要求 |
|---|---|
| JDK | **8 及以上**（代码只用到 Java 8 的 lambda 与 Stream） |
| 构建工具 | **不需要**（无 Maven / Gradle） |
| 第三方库 | **无**（只用 JDK 自带的 Swing、AWT、ImageIO） |
| 操作系统 | 任意支持 Java 的系统（Windows / macOS / Linux 均可） |

---

## 快速开始

### 方式一：IDEA（推荐新手用这个）

1. `File` → `Open`，选择项目根目录 `animal_chess`
2. 确认 `src` 目录被标记为源码根（右键 `src` → `Mark Directory as` → `Sources Root`）
3. 运行 `src/Main.java`

**注意**：运行时工作目录必须是项目根目录（因为图片是按 `user.dir` 加载的，见[已知不足](#已知不足与待办)）。IDEA 默认就是这样，一般不用改。

### 方式二：命令行

在**项目根目录**下执行：

```bash
# 编译
javac -encoding UTF-8 -d out $(find src -name "*.java")

# 运行（必须保持工作目录在项目根）
java -cp out Main
```

Windows CMD 下：

```bat
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out Main
```

### 方式三：打包成 jar 分发

```bat
:: 编译
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt

:: 打包（把 images 一起塞进 jar 可选，见下）
jar cfe animal_chess.jar Main -C out .

:: 运行时需要保证 images 目录和 jar 在同一个目录下
java -jar animal_chess.jar
```

给朋友体验时，把 `animal_chess.jar` 和 `images/` 目录一起压缩成 zip 发送即可。**请提醒对方先解压再运行**，不要直接在压缩包里双击。

---

## 游戏规则

如果你不熟悉斗兽棋，先看这张表——**看懂了规则再读代码会轻松很多**。

### 棋子与战力

| 棋子 | 战力 | 说明 |
|---|---|---|
| 象 | 100 | 最强，但不能过河 |
| 狮 | 70 | 可跳河 |
| 虎 | 60 | 可跳河 |
| 豹 | 50 | |
| 狼 | 40 | |
| 狗 | 30 | |
| 猫 | 20 | |
| 鼠 | 10 | 最弱，但**可以吃象**，且唯一能下水 |

### 核心规则

1. **移动**：每回合只能向上下左右走一格
2. **吃子**：走到对方棋子所在的格子，战力**大于或等于**对方即可吃掉（同级时先手方获胜）
3. **鼠吃象**：老鼠可以吃掉大象，大象不能吃老鼠
4. **河流**：只有老鼠能进入水中；其他棋子不能过河
5. **水陆限制**：老鼠不能在水里和岸上之间直接吃子
6. **跳河**：狮子和老虎可以横着或竖着跳过整段河流（中间必须全是水，且河里没有老鼠）
7. **陷阱**：位于兽穴旁边的 3 个格子。棋子进入**对方**陷阱后战力变为 0，可以被打败；走出陷阱后战力恢复。自己的陷阱对己方棋子无影响
8. **兽穴**：不能进入自己的兽穴；进入对方兽穴立刻获胜
9. **胜负**：进入对方兽穴，或吃光对方全部棋子

---

## 项目结构

```
animal_chess/
├── src/
│   ├── Main.java                              入口，启动窗口
│   └── com/mingye/
│       ├── components/                        视图层
│       │   ├── GameFrame.java                 主窗口 + 菜单栏
│       │   └── BoardPanel.java                棋盘绘制 + 胜利弹窗
│       ├── listener/                          交互层
│       │   └── BoardClickListener.java        处理鼠标点击
│       ├── service/                           业务层
│       │   └── GameService.java               规则引擎（初始化 / 走子 / 胜负）
│       ├── model/                             数据层
│       │   ├── Grid.java                      一个格子
│       │   ├── GridTypeEnum.java              格子类型：陆地 / 河 / 陷阱 / 兽穴
│       │   └── Piece.java                     一枚棋子（实现 Comparable）
│       ├── constants/                         常量
│       │   ├── GameConstants.java             棋盘尺寸、行列数、边距
│       │   └── PieceConstants.java            棋子名称 + 战力表
│       └── utils/                             工具
│           ├── BoardArrayUtils.java           坐标换算、移动、跳河判断
│           └── ImageUtils.java                图片加载
├── images/                                    20 张图片资源
└── docs/
    └── screenshot.png
```

### 分层约定

| 层 | 职责 | 不该做的事 |
|---|---|---|
| `components` | 只负责**画**和**弹窗** | 不写游戏规则判断 |
| `listener` | 只负责**把事件翻译成调用** | 不写规则，也不画图 |
| `service` | 所有**规则判断**都在这里 | 不碰 Graphics，不碰 Swing |
| `model` | 只存**状态**（字段 + getter/setter） | 不写业务方法 |
| `constants` | 集中管理**常量与战力表** | 不放逻辑 |
| `utils` | **无状态**的纯工具方法 | 不持有可变状态 |

这个划分的好处：**规则和界面完全解耦**。想加一个 AI 对手，只需要让 AI 去调用 `GameService`，`BoardPanel` 一行都不用改。

---

## 代码导读：一次点击发生了什么

如果你只看一个流程，看这个。从鼠标按下到界面刷新，完整链路如下：

```
用户点击鼠标
    ↓
【BoardClickListener.mouseReleased】            ← listener 层
    ① 判断对局是否已结束，结束就直接返回
    ② 校验点击坐标是否在棋盘范围内，点在外面就忽略
    ③ 把像素坐标换算成对应的格子 Grid
    ↓
【GameService.doPlay(grid)】                    ← service 层，规则都在这里
    ├─ 点的是空格子 + 已选中棋子 + 位置可达  → 移动棋子，返回 true
    ├─ 点的是己方棋子                        → 选中它，返回 false
    ├─ 点的是敌方棋子 + 可达 + 战力足够      → 吃掉它，返回 true
    └─ 其他情况                              → 无效操作，返回 false
    ↓
【BoardPanel.repaint()】                        ← components 层
    重绘棋盘：线条 → 河流 → 陷阱 → 兽穴 → 棋子 → 选中框
    ↓
【GameService.checkWin()】
    有胜方 → 弹出胜利对话框
    无胜方 → 如果 doPlay 返回 true，切换行动方
```

### 建议的阅读顺序

1. `GameConstants` + `PieceConstants` —— 先知道棋盘多大、棋子有哪些
2. `Grid` + `GridTypeEnum` + `Piece` —— 三个数据模型，都很短
3. `GameService.initBoardArr()` / `initRiver()` / `initTrap()` / `initCave()` / `initPiece()` —— 看懂初始状态怎么摆出来的
4. `GameService.doPlay()` —— 核心，走子与吃子
5. `GameService.checkGridCanMove()` —— 规则最密集的地方，各种条件都在这里
6. `BoardArrayUtils` 的 `isAllRiverBetween()` / `hasMouseInRiver()` —— 跳河的两条判断
7. `BoardClickListener` + `BoardPanel` —— 最后看界面，前面看懂了这里很快

---

## 几个值得一说的设计

如果你是有经验的开发者，可以直接看这一节。

### 1. `doPlay` 用一个 boolean 表达四种交互状态

```java
// true  = 本次是有效操作，需要换手
// false = 无效点击，或者只是选中了己方棋子
boolean changePlayerFlag = service.doPlay(currentGrid);
```

点击棋盘其实有四种可能的语义（选中 / 移动 / 吃子 / 无效），但对外只暴露一个 boolean。调用方不需要知道任何规则细节，也不需要在监听器里重复判断一遍——**规则只有一处实现**。

### 2. 用 `Comparable` + 战力极值实现"鼠吃象"

```java
@Override
public int compareTo(Piece o) {
    if (this.getPower() == MIN_ANIMAL_POWER && o.getPower() == MAX_ANIMAL_POWER) {
        return 1;   // 鼠吃象
    }
    if (this.getPower() == MAX_ANIMAL_POWER && o.getPower() == MIN_ANIMAL_POWER) {
        return -1;  // 象不能吃鼠
    }
    ...
}
```

特殊规则被塞进了比较逻辑里，而不是在吃子处到处写 `if (name.equals("mouse") && ...)`。

战力表的取值也有讲究：`10, 20, 30, 40, 50, 60, 70`，然后直接跳到 `100`。

```java
public static final int MIN_ANIMAL_POWER = 10;    // 鼠
public static final int MAX_ANIMAL_POWER = 100;   // 象
```

这样做的好处：`MIN` / `MAX` 成了明确的极值标记，`compareTo` 里判断"鼠吃象"时不用写死具体数字；同时 70 到 100 之间留出了空位，将来想插入新棋子不会挤爆现有战力。

### 3. 跳河拆成两个纯函数

```java
BoardArrayUtils.isAllRiverBetween(src, target, boardArr);  // 两点之间是不是全是水
BoardArrayUtils.hasMouseInRiver(src, target, boardArr);    // 水里有没有老鼠
```

两个方法都只依赖入参，不读任何外部状态——这种纯函数**最容易单独测试**，也最容易复用。

### 4. 陷阱的战力变化集中在 `movePiece` 一处

```java
public static void movePiece(Piece piece, Grid srcGrid, Grid targetGrid) {
    targetGrid.setPiece(piece);
    srcGrid.setPiece(null);

    if (进入敌方陷阱) {
        piece.setPower(NO_POWER);        // 归零
    } else if (piece.getPower() == NO_POWER) {
        piece.setPower(原始战力);         // 走出恢复
    }
}
```

"进入"和"走出"两个方向的判断放在同一个方法里，不会出现改了一边漏了另一边的情况。

---

## 已知不足与待办

这个项目写的时候还在学习阶段，下面这些都是**我自己知道的问题**，列出来是因为觉得对读者也有参考价值：

### 代码层面

- **绘制层有硬编码坐标**。`BoardPanel.drawRivers()` 里河流的绘制位置是写死的 `1 * GRID + margin` 和 `4 * GRID + margin`，但 `GameService.initRiver()` 里是用循环按行列设置的。**同一件事有两个数据来源**，改棋盘布局时要同时改两处。更好的做法是绘制时遍历 `boardArr` 判断 `GridTypeEnum.RIVER`，不重复定义坐标。
- **棋子和棋盘是两份数据**。`GameService` 里既有 `pieceList`，`Grid` 上又挂了 `piece` 引用，靠走子流程保证同步。正常玩不会出错，但设计上存在不一致风险。
- **`checkGridCanMove()` 用了隐式状态**。它内部读的是 `selectedPiece` 字段而不是通过参数传入，导致这个方法无法独立测试。改成 `checkGridCanMove(Piece moving, Grid src, Grid target)` 会更好。
- **`checkWin()` 有副作用**。一个看起来像查询的方法，内部顺手改了 `gameOverFlag`。
- **资源路径依赖工作目录**。`ImageUtils` 用 `System.getProperty("user.dir")` 定位图片，意味着**必须在项目根目录启动**。打包后如果在别的目录运行或双击 jar，会找不到图片。改成基于 jar 自身位置定位、或用 classpath 读取会更稳。
- **`ImageUtils` 里有一处死代码**：`black_cannon.png` 是从中国象棋项目复制过来忘删的（后面被 `black_mouse.png` 覆盖了，所以不影响运行）。另外 `images/closed.png` 代码里没有引用。
- **控制台输出**。`GameService` 和 `BoardArrayUtils` 里保留了若干 `System.out.println`，用来打印当前玩家、规则拦截原因等。学习阶段这是有意留的观察窗口，正式发布时可以换成日志框架。

### 工程层面

- **没有单元测试**。规则逻辑有十几条且互相耦合，最需要回归测试。这块是我目前最想补的。
- **没有构建文件**。有意不引入 Maven——为了让人打开就能跑，不卡在依赖下载上。代价是没有标准的 `mvn package` 流程。

### 想加的功能

- [ ] 悔棋 / 撤销
- [ ] AI 对手（目前必须两个人玩）
- [ ] 走子动画与音效
- [ ] 可选落子位置高亮提示
- [ ] 计步与计时
- [ ] 自动化测试

欢迎认领其中任何一项，或者提你觉得更好的想法。

---

## 反馈与交流

- 发现 bug 或规则实现有误 → 提 [Issue](https://github.com/MyCafe/animal_chess/issues)，最好附上复现步骤和截图
- 想改代码 → 欢迎 PR，建议先开 Issue 讨论一下方向
- 觉得对你有帮助 → 点个 Star 就是最好的鼓励

如果这个项目帮你搞懂了 Swing 的事件机制、或者看懂了一个游戏该怎么分层，那它的目的就达到了。

---

## 开源协议

本项目采用 **木兰宽松许可证，第 2 版（Mulan PSL v2）**。

你可以自由使用、修改、分发，包括商业用途，只需保留版权声明与许可证文本。

---

## 作者

**daixinyi**

- Gitee：[@coffee-style](https://gitee.com/coffee-style)
- GitHub：[@MyCafe](https://github.com/MyCafe)

同时也欢迎看看我的其他小项目：中国象棋、五子棋、2048、推箱子、翻麻将、生日计算器等，都是同样风格的纯 Java Swing 实现。
