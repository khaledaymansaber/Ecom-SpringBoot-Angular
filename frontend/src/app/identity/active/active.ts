import { Component, OnInit } from '@angular/core'; // تم التعديل إلى OnInit
import { ActiveAccount } from '../../shared/Models/ActiveAccount';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Identity } from '../identity';

@Component({
  selector: 'app-active',
  standalone: false,
  styleUrl: './active.scss',
  templateUrl: './active.html',
})
export class Active implements OnInit {
  activeParam: ActiveAccount =new ActiveAccount()



  constructor(
    private router: ActivatedRoute,
    private _service: Identity,
    private _toast: ToastrService,
    private route: Router

  ) {}

  ngOnInit(): void {
    this.router.queryParams.subscribe(param => {


      console.log("Query Params:", param);

      this.activeParam.email = param['email'];
      this.activeParam.token = param['code'];


      if (this.activeParam.email && this.activeParam.token) {

        this._service.active(this.activeParam).subscribe({
          next: (value) => {
            console.log("Success Response:", value);
            this._toast.success("Your account is active", "SUCCESS");
            this.route.navigateByUrl('/Account/Login')
          },
          error: (err) => {

            console.error("Error Details:", err);


            const errorMsg = err?.error?.message || err?.message || "Your account is not active, token is expired";
            this._toast.error(errorMsg, "ERROR");
          },
        });

      } else {
        console.warn("Email or Token is missing from the URL!");
      }
    });
  }
}
