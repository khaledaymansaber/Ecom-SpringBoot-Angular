import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Register } from './register/register';
import { Active } from './active/active';
import { Login } from './login/login';
import { ResetPassword } from './reset-password/reset-password';

const routes: Routes = [
  {path:'Register',component:Register},
  {path:'active',component:Active},
  {path:'Login',component:Login},
  {path:'reset',component:ResetPassword}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class IdentityRoutingModule {}
