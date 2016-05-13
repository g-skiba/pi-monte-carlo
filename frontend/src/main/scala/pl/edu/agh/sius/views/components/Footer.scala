package pl.edu.agh.sius.views.components
import pl.edu.agh.sius.config.ExternalUrls
import pl.edu.agh.sius.styles.{DemoStyles, GlobalStyles}
import pl.edu.agh.sius.styles.partials.FooterStyles
import org.scalajs.dom.raw.Element

import scalatags.JsDom.all._
import scalacss.ScalatagsCss._

object Footer {
  private lazy val template = footer(FooterStyles.footer)(
    div(GlobalStyles.body)(
      div(FooterStyles.footerInner)(
        a(FooterStyles.footerLogo, href := ExternalUrls.homepage)(
          Image("udash_logo.png", "Udash Framework", GlobalStyles.block)
        ),
        div(FooterStyles.footerLinks)(
          p(FooterStyles.footerMore)("See more"),
          ul(
            li(DemoStyles.navItem)(
              a(href := ExternalUrls.udashDemos, target := "_blank", DemoStyles.underlineLink)("Github demo")
            ),
            li(DemoStyles.navItem)(
              a(href := ExternalUrls.stackoverflow, target := "_blank", DemoStyles.underlineLink)("StackOverflow questions")
            )
          )
        ),
        p(FooterStyles.footerCopyrights)("Proudly made by ", a(FooterStyles.footerAvsystemLink, href := ExternalUrls.avsystem, target := "_blank")("AVSystem"))
      )
    )
  ).render

  def getTemplate: Element = template
}