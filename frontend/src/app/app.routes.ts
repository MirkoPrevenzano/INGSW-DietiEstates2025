import { Routes } from '@angular/router';
import { LoginComponent } from './componentView/sign-in/login.component';
import { PasswordChangeComponent } from './componentView/password-change/password-change.component';
import { CreateEstatesComponent } from './componentView/create-estates-components/create-estates/create-estates.component';
import { CustomerRegistrateComponent } from './componentView/register-users/customer-registrate/customer-registrate.component';
import { AgentRegistrateComponent } from './componentView/register-users/agent-registrate/agent-registrate.component';
import { AdminRegistrateComponent } from './componentView/register-users/admin-registrate/admin-registrate.component';
import { AgentHomeComponent } from './componentView/agent-home/agent-home.component';
import { AdminHomeComponent } from './componentView/admin-home/admin-home.component';
import { CustomerHomeComponent } from './componentView/customer-home/customer-home.component';
import { HomeComponent } from './componentView/home/home.component';
import { EstateItemPreviewComponent } from './componentView/_estate-view/estate-item-preview/estate-item-preview.component';
import { EstateFiltersComponent } from './componentView/estate-filters/estate-filters.component';
import { EstateSearchContainerComponent } from './componentView/estate-search-container/estate-search-container.component';
import { AgentDashboardComponent } from './componentView/agent-dashboard/agent-dashboard.component';
import { authGuard } from './_guard/authentication/auth.guard';
import { roleGuard } from './_guard/authorization/role.guard';
import { notAuthGuard } from './_guard/not-auth/no-auth.guard';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        title: 'Login',
        path: 'login',
        canActivate:[notAuthGuard],
        component:LoginComponent
    },
    {
        title:'Register',
        path: 'register',
        canActivate:[notAuthGuard],
        component:CustomerRegistrateComponent
    },
    {
        title:'Password change',
        path: 'admin/change-password',
        component: PasswordChangeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_ADMIN'}
    },
    {
        title:'Save real estates agent',
        path: 'admin/create-agent',
        component: AgentRegistrateComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_ADMIN'}
    },
    {
        title:'Save collaborator',
        path: 'admin/create-collaborator',
        component: AdminRegistrateComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_ADMIN'}
    },
   
    {
        title: 'create estate form',
        path: 'create-estate',
        component: CreateEstatesComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_AGENT'}
    },
    {
        title: 'agent home',
        path: 'home/agent',
        component: AgentHomeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_AGENT'}
    },
    {
        title:'Home Admin',
        path: 'home/admin',
        component: AdminHomeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_ADMIN'}
    },
    {
        title:'Home Customer',
        path: 'home/customer',
        component: CustomerHomeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_USER'}
    },
    {
        title: 'HomePage',
        path: 'home',
        component: HomeComponent
    },
    {
        title: 'HomePage',
        path: 'estate',
        component: EstateItemPreviewComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_USER'}
    },
    {
        title: 'Filter',
        path: 'filter',
        component: EstateFiltersComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_USER'}
    },{
        title:'container',
        path: 'container',
        component:EstateSearchContainerComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_USER'}
    },
    {
        title:'dashboard',
        path: 'dashboard',
        component: AgentDashboardComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:'ROLE_AGENT'}
    }
   
];
