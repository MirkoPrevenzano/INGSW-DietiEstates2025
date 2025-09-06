import { Routes } from '@angular/router';
import { LoginComponent } from './componentPage/sign-in/login.component';
import { PasswordChangeComponent } from './componentPage/password-change/password-change.component';
import { CreateEstatesComponent } from './componentPage/create-estates-components/create-estates/create-estates.component';
import { CustomerRegistrateComponent } from './componentPage/register-users/customer-registrate/customer-registrate.component';
import { AgentRegistrateComponent } from './componentPage/register-users/agent-registrate/agent-registrate.component';
import { AdminRegistrateComponent } from './componentPage/register-users/admin-registrate/admin-registrate.component';
import { AgentHomeComponent } from './componentPage/agent-home/agent-home.component';
import { AdminHomeComponent } from './componentPage/admin-home/admin-home.component';
import { CustomerHomeComponent } from './componentPage/customer-home/customer-home.component';
import { HomeComponent } from './componentPage/home/home.component';
import { EstateItemPreviewComponent } from './componentPage/_estate-view/estate-item-preview/estate-item-preview.component';
import { EstateFiltersComponent } from './componentPage/estate-search-container/estate-filters/estate-filters.component';
import { EstateSearchContainerComponent } from './componentPage/estate-search-container/estate-search-container.component';
import { AgentDashboardComponent } from './componentPage/agent-dashboard/agent-dashboard.component';
import { authGuard } from './_guard/authentication/auth.guard';
import { roleGuard } from './_guard/authorization/role.guard';
import { notAuthGuard } from './_guard/not-auth/no-auth.guard';
import { EstateItemDetailComponent } from './componentPage/_estate-view/estate-item-detail/estate-item-detail.component';
import { RedirectGuard } from './_guard/redirect-home/redirect.guard';
import { RegisterAgencyComponent } from './componentPage/register-agency/register-agency.component';

export const routes: Routes = [
    // Route di default per path vuoto
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
        title: 'Create Agency',
        path: 'create-agency',
        canActivate:[notAuthGuard],
        component: RegisterAgencyComponent
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
        data:{expectedRole:['ROLE_ADMIN','ROLE_COLLABORATOR','ROLE_UNAUTHORIZED']}
    },
    {
        title:'Save real estates agent',
        path: 'admin/create-agent',
        component: AgentRegistrateComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_ADMIN','ROLE_COLLABORATOR']}
    },
    {
        title:'Save collaborator',
        path: 'admin/create-collaborator',
        component: AdminRegistrateComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_ADMIN']}
    },
   
    {
        title: 'create estate form',
        path: 'create-estate',
        component: CreateEstatesComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_AGENT']}
    },
    {
        title: 'agent home',
        path: 'home/agent',
        component: AgentHomeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_AGENT']}
    },
    {
        title:'Home Admin',
        path: 'home/admin',
        component: AdminHomeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_ADMIN','ROLE_COLLABORATOR']}
    },
    {
        title:'Home Customer',
        path: 'home/customer',
        component: CustomerHomeComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_CUSTOMER']}
    },
    {
        title: 'HomePage',
        path: 'home',
        canActivate:[notAuthGuard],
        component: HomeComponent
    },
    {
        title: 'HomePage',
        path: 'estate',
        component: EstateItemPreviewComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_CUSTOMER']}
    },
    {
        title:'EstateDetail',
        path:'estate/:id',
        component: EstateItemDetailComponent,
        data:{expectedRole:['ROLE_CUSTOMER','ROLE_AGENT']}

       
    },
    {
        title: 'Filter',
        path: 'filter',
        component: EstateFiltersComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_CUSTOMER']}
    },{
        title:'container',
        path: 'container',
        component:EstateSearchContainerComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_CUSTOMER']}
    },
    {
        title:'dashboard',
        path: 'dashboard',
        component: AgentDashboardComponent,
        canActivate: [authGuard, roleGuard],
        data:{expectedRole:['ROLE_AGENT']}
    },
    {
        path: '**',
        canActivate: [RedirectGuard],
        component: HomeComponent
    }
   
];

