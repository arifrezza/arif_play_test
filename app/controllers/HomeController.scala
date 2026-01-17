package controllers

import models.UserData
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import javax.inject._


@Singleton
class HomeController @Inject()(val cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  //Define a form mapping for UserData
  val userForm:Form[UserData] = Form(
    mapping(
      //"name" -> nonEmptyText,
      "name" -> text.verifying("name.required", _.trim.nonEmpty),
      "age" -> text
        .verifying("age.required", _.trim.nonEmpty)
        .verifying("age.numeric", a => a.forall(_.isDigit))
        .transform[Int](_.toInt, _.toString)
        .verifying("age.range", age => age >= 0 && age <= 120)
    )(UserData.apply)(UserData.unapply)
  )

  // Action to render the index page
  def index(): Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.index(userForm))
  }

  // Action to handle form submission
  def processForm: Action[AnyContent] = Action{
    implicit request: MessagesRequest[AnyContent] =>
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
