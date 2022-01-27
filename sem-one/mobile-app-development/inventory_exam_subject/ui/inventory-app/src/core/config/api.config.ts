import * as qs from 'qs';
import {AxiosRequestConfig} from "axios";
import {Environment} from "../../environment/Environment";
import {PathLike} from "fs";

export const ApiConfig : AxiosRequestConfig = {
    baseURL: Environment.apiUrl,
    paramsSerializer: (params: PathLike) => qs.stringify(params, { indices: false })
}
