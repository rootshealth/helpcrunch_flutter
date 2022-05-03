import Flutter
import UIKit
import HelpCrunchSDK


public class SwiftHelpcrunchPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
      HelpCrunchPluginSetup(registrar.messenger(), HelpCrunchPluginImpl.init())
  }
}


public class HelpCrunchPluginImpl : NSObject,  HelpCrunchPlugin {

    public func initializeParams(_ params: HelpCrunchInitializationParams, completion: (@escaping (FlutterError?) -> Void)) -> Void {
         let configuration =
                HCSConfiguration(forOrganization: params.organizationName,
                applicationId: params.helpCrunchAppId.stringValue,
                applicationSecret: params.appSecret)
         configuration.shouldUsePushNotificationDelegate = params.iOSShouldUsePushNotificationDelegate.boolValue
         HelpCrunch.initWith(configuration, user: nil) { (error) in
                   guard let error = error else {
                           completion(nil)
                           return
                   }
                   completion(FlutterError.init(code: String(error._code), message: error.localizedDescription, details: error.localizedDescription))

        }
    }


  public func showChatScreen(completion: (@escaping (FlutterError?) -> Void)) {
    guard let viewController: UIViewController =
                          (UIApplication.shared.delegate?.window??.rootViewController) else {
                            completion(FlutterError.init(code: "NO_VIEWCONTROLLER", message: "viewController is nil", details: "viewController is nil"))
                            return
    }

    HelpCrunch.show(from: viewController) { (error) in
         guard let error = error else {
                     completion(nil)
                     return
         }

         completion(FlutterError.init(code: String(error._code), message: error.localizedDescription, details: error.localizedDescription))

      }
  }

  public func logoutUser(completion: (@escaping (FlutterError?) -> Void)) -> Void  {
       HelpCrunch.logout { (error) in

          guard let error = error else {
            completion(nil)
            return
          }

          completion(FlutterError.init(code: String(error._code), message: error.localizedDescription, details: error.localizedDescription))

       }
  }

  public func updateUserUser(_ user: HelpCrunchUser , completion: (@escaping (FlutterError?) -> Void)) -> Void  {
        let hcsUser = HCSUser()
        hcsUser.userId = user.id
        hcsUser.name = user.name
        hcsUser.email = user.email
        hcsUser.phone = user.phone
        hcsUser.company = user.company
        user.customData = user.customData
        HelpCrunch.update(hcsUser) { (error) in
                guard let error = error else {
                          completion(nil)
                          return
                }

                completion(FlutterError.init(code: String(error._code), message: error.localizedDescription, details: error.localizedDescription))

        }



  }

  public func registerForRemoteMessages( completion: (@escaping (FlutterError?) -> Void)) -> Void {
        HelpCrunch.registerForRemoteNotifications()
        completion(nil)
  }

  public func getNumberOfUnreadChats(completion: (@escaping (NSNumber?, FlutterError?) -> Void)) -> Void {
        let count = HelpCrunch.numberOfUnreadChats()
        completion(NSNumber(value: count), nil)
  }

  public func isReadyWithError(_ error: AutoreleasingUnsafeMutablePointer<FlutterError?>) -> NSNumber? {
      return NSNumber(value:HelpCrunch.state() == HCSState.readyState)
  }

  public  func hasErrorWithError(_ error: AutoreleasingUnsafeMutablePointer<FlutterError?>) -> NSNumber? {
      return NSNumber(value: HelpCrunch.state() == HCSState.errorState)
  }

}