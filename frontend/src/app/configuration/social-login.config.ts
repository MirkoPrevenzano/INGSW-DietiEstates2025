import { SocialAuthServiceConfig, GoogleLoginProvider } from '@abacritt/angularx-social-login';
import { environment } from '../../environments/environment.prod';

export const socialAuthConfig: SocialAuthServiceConfig = {
  autoLogin: false,
  providers: [
    {
      id: GoogleLoginProvider.PROVIDER_ID,
      provider: new GoogleLoginProvider(environment.googleApiKey)
    }
  ]
};

export const socialAuthProviders = [
  {
    provide: 'SocialAuthServiceConfig',
    useValue: socialAuthConfig
  }
];