import { Component, Input, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-not-found',
    imports: [RouterLink],
    templateUrl: './not-found.component.html',
    styleUrl: './not-found.component.scss'
})


export class NotFoundComponent implements OnInit{
    
    @Input() textMessage:string ="Sorry, we couldn’t find the page you’re looking for."
    url="/home"

    ngOnInit(): void {
        const role = localStorage.getItem("role")
        if(role==="ROLE_USER")
            this.url+="/customer"
        if(role==="ROLE_ADMIN")
            this.url+="/admin"
        
    }

    
}
