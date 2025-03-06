export interface AuthState {
    user: string | null;
    token: string | null;
    role: string | null
    isAuthenticated: boolean;
}