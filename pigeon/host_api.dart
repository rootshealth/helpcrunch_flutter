import 'package:pigeon/pigeon.dart';

/// code used to generate the pigeon api
/// see README for details

class HelpCrunchInitializationParams {
  final int helpCrunchAppId;

  final String organizationName;

  final String appSecret;

  final bool iOSShouldUsePushNotificationDelegate;

  final int? notificationColor;

  HelpCrunchInitializationParams({
    required this.helpCrunchAppId,
    required this.organizationName,
    required this.appSecret,
    required this.iOSShouldUsePushNotificationDelegate,
    required this.notificationColor,
  });
}

class HelpCrunchUser {
  final String id;
  final String? name;
  final String? email;
  final String? company;
  final String? phone;
  final Map<String?, Object?>? customData;

  HelpCrunchUser( {
    required this.id,
    this.name,
    this.email,
    this.company,
    this.phone,
    this.customData,
  });
}

@HostApi()
abstract class HelpCrunchPlugin {
  /// Initializes the HelpCrunch plugin.
  /// This method must be called before any other method.
  @async
  void initialize(HelpCrunchInitializationParams params);

  /// show the native chat screen
  @async
  void showChatScreen();

  @async
  void updateUser(HelpCrunchUser user);

  @async
  void logoutUser();

  @async
  void registerForRemoteMessages();

  @async
  int getNumberOfUnreadChats();

  bool isReady();

  bool hasError();
}
