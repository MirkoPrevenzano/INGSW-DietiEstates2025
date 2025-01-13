import { computed, effect, Injectable, signal } from '@angular/core';
import { AuthState } from './auth-state.type';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  

  authState= signal<AuthState>({
    user: this.getUser(),
    token: this.getToken(),
    role: this.getRoleByToken(),
    isAuthenticated: this.verifyToken(this.getToken()) 
  })

  user = computed(() => this.authState().user); 
  token = computed(() => this.authState().token);
  isAuthenticated = computed(() => this.authState().isAuthenticated);

  constructor(){
    effect( () => { //this effect will run every time authState changes
      const token = this.authState().token;
      const user = this.authState().user;
      const role = this.authState().role;
      if(token !== null){
        localStorage.setItem("token", token);
      } else {
        localStorage.removeItem("token");
      }
      if(user !== null){
        localStorage.setItem("user", user);
      } else {
        localStorage.removeItem("user");
      }
      if(role !== null){
        localStorage.setItem("role", role);
      } else {
        localStorage.removeItem("role");
      }
    });
  }

  updateToken(token: string): void {
    const decodedToken: any = jwtDecode(token);
    console.log(decodedToken)
    const user = decodedToken.sub;
    console.log(user)
    const role = decodedToken.role;
    this.authState.set({
      user: user,
      token: token,
      role: role,
      isAuthenticated: this.verifyToken(token)
    })
  }

  private verifyToken(token: string | null): boolean {
    if(token !== null){
      try{
        const decodedToken = jwtDecode(token); 
        const ttl = decodedToken.exp; 

        //essendo che date.now restituisce il tempo in millisecondi, moltiplico ttl per 1000
        if(ttl === undefined || Date.now() >= ttl * 1000){ 
          return false; 
        } else {
          return true; 
        }
      } catch(error) {  
        return false;
      }
    }
    return false;
  }
  
  
  private getRoleByToken(){
    const token= this.getToken()
    if(token){
      const decodedToken: any= jwtDecode(token)
      return decodedToken ? decodedToken.role : null
    }
    return null
  }
  isUserAuthenticated(): boolean { return this.verifyToken(this.getToken()); }


  getUser(): string | null { return localStorage.getItem("user"); }

  
  getToken(): string | null { return localStorage.getItem("token"); }

  
  getRole(): string | null {return this.authState().role;}


  logout(){ this.authState.set({user: null, token: null,role: null, isAuthenticated: false }); }

  
}



