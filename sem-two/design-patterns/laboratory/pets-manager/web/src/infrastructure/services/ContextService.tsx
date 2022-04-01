export class ContextService{
    public static isAuthenticated = () : boolean => {
        return localStorage.getItem('current-user') != null && localStorage.getItem('current-user') !== undefined;
    }
    public static logout = (): void => {
        localStorage.removeItem('current-user');
    }
}