import java.awt.{Graphics2D, Color, RenderingHints, Font}
import java.awt.event.{ActionListener, ActionEvent}
import javax.swing.{JFrame, JPanel, Timer, WindowConstants}
import scala.collection.mutable.ListBuffer
import scala.util.Random

// 这里的 Spark 代表火花粒子
case class 火花(var 横坐标: Double, var 纵坐标: Double, 速度X: Double, 速度Y: Double, 颜色: Color, var 透明度: Float)

class 烟花面板 extends JPanel {
  private val 火花列表 = ListBuffer[火花]()
  private val 随机生成 = new Random()

  // 动画计时器 (约 60 帧/秒)
  private val 计时器 = new Timer(16, new ActionListener {
    override def actionPerformed(e: ActionEvent): Unit = {
      更新火花状态()
      repaint()
    }
  })
  计时器.start()

  override def paintComponent(绘图对象: java.awt.Graphics): Unit = {
    val 画笔 = 绘图对象.asInstanceOf[Graphics2D]
    画笔.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    // 绘制背景：深红色底色
    画笔.setColor(new Color(25, 0, 0))
    画笔.fillRect(0, 0, getWidth, getHeight)

    // 绘制主标题
    画笔.setFont(new Font("Microsoft YaHei", Font.BOLD, 80))
    画笔.setColor(new Color(255, 215, 0)) // 金色
    画笔.drawString("恭喜发财", getWidth / 2 - 160, getHeight / 2)

    // 绘制副标题
    画笔.setFont(new Font("Microsoft YaHei", Font.PLAIN, 25))
    画笔.setColor(Color.WHITE)
    画笔.drawString("马到成功 · 2026 丙午年", getWidth / 2 - 130, getHeight / 2 + 60)

    // 渲染所有火花
    火花列表.foreach { 粒子 =>
      画笔.setColor(new Color(粒子.颜色.getRed, 粒子.颜色.getGreen, 粒子.颜色.getBlue, (粒子.透明度 * 255).toInt))
      画笔.fillOval(粒子.横坐标.toInt, 粒子.纵坐标.toInt, 4, 4)
    }
  }

  private def 更新火花状态(): Unit = {
    火花列表.foreach { p =>
      p.横坐标 += p.速度X
      p.纵坐标 += p.速度Y + 0.1 // 模拟重力系数
      p.透明度 *= 0.96f        // 模拟能量耗散
    }
    // 移除已经消失的火花
    火花列表.filterInPlace(_.透明度 > 0.05f)

    // 随机自动触发烟花
    if (随机生成.nextInt(25) == 0) 创建烟花爆炸(随机生成.nextInt(getWidth), 随机生成.nextInt(getHeight))
  }

  def 创建烟花爆炸(x: Int, y: Int): Unit = {
    val 随机颜色 = new Color(Color.HSBtoRGB(随机生成.nextFloat(), 0.8f, 1.0f))
    for (_ <- 1 to 40) {
      val 弧度 = 随机生成.nextDouble() * 2 * Math.PI
      val 速率 = 随机生成.nextDouble() * 5 + 1
      火花列表 += 火花(
        x, y, 
        Math.cos(弧度) * 速率, 
        Math.sin(弧度) * 速率, 
        随机颜色, 
        1.0f
      )
    }
  }
}

object GoxingFaCai extends App {
  val 窗口 = new JFrame("2026 农历新年祝贺")
  窗口.add(new 烟花面板())
  窗口.setSize(800, 600)
  窗口.setLocationRelativeTo(null)
  窗口.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  窗口.setVisible(true)
}
