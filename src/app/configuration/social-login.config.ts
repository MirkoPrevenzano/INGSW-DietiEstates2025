import { SocialAuthServiceConfig, GoogleLoginProvider } from '@abacritt/angularx-social-login';

export const socialAuthConfig: SocialAuthServiceConfig = {
  autoLogin: false,
  providers: [
    {
      id: GoogleLoginProvider.PROVIDER_ID,
      provider: new GoogleLoginProvider('699354462746-9ale2lg8onjqvafu9aiopmd0fo82j3b4.apps.googleusercontent.com')
    }
  ]
};

export const socialAuthProviders = [
  {
    provide: 'SocialAuthServiceConfig',
    useValue: socialAuthConfig
  }
];