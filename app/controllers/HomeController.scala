package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.mvc._
import play.api.data.Forms._
import models.UserData
import play.api.data.Form
import play.api.i18n._


@Singleton
class HomeController @Inject()(val cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  //Define a form mapping for UserData
  val userForm:Form[UserData] = Form(
    mapping(
      "name" -> nonEmptyText,
      "age" -> number(min = 0, max = 120)
    )(UserData.apply)(UserData.unapply)
  )

  // Action to render the index page
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(userForm))
  }

  // Action to handle form submission
  def processForm: Action[AnyContent] = Action{
    implicit request: Request[AnyContent] =>
      userForm.bindFromRequest.fold(
        formWithErrors => {
          // If there are errors in the form, re-render the page with errors
          BadRequest(views.html.index(formWithErrors))
        },
        UserData => {
          // If the form is valid, process the data (e.g., save to database)
          // For demonstration, we just render a success page
          Ok(views.html.result(UserData))
        }
      )
  }
}
