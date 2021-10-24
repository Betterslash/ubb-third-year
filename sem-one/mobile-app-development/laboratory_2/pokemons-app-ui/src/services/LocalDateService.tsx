export class LocalDateService{
    public static parseToJavaLocalDate(value : string){
        return value.split("T")[0];
    }
}
