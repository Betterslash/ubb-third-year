import ApiService from "./api.service";
import {Product} from "../model/product.model";
import {Environment} from "../../environment/Environment";
import {AxiosResponse} from "axios";

class ProductService{
    private static readonly PRODUCT_API_URL = `${Environment.apiUrl}product`;

    getAll = ():Promise<AxiosResponse<Product[]>> => {
        return ApiService.get<Product[]>(ProductService.PRODUCT_API_URL);
    }
}

export default new ProductService();
