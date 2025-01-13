import { Routes } from '@angular/router';
import { LoginComponent } from './componentView/sign-in/login.component';
import { RegisterComponent } from './componentView/register/register.component';
import { PasswordChangeComponent } from './componentView/password-change/password-change.component';
import { authGuard } from './_guard/authentication/auth.guard';
import { roleGuard } from './_guard/authorization/role.guard';
import { RegistrateAgentComponent } from './componentView/registrate-agent/registrate-agent.component';

export const routes: Routes = [
    {
        title: 'Login',
        path: 'login',
        component:LoginComponent
    },
    {
        title:'Register',
        path: 'register',
        component:RegisterComponent
    },
    {
        title:'Password change',
        path: 'admin/newPassword',
        component: PasswordChangeComponent,
        //canActivate: [authGuard, roleGuard],
        //data:{expectedRole:'Admin'}
    },
    {
        title:'Save real estates agent',
        path: 'agent/save',
        component: RegistrateAgentComponent,
        //canActivate: [authGuard, roleGuard],
        //data:{expectedRole:'Admin'}
    }
   
];
