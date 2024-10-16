import {Injectable} from '@angular/core';
import {City} from "../../models/city.model";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment.development"
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CityService {

  constructor(private http : HttpClient) {}

  getCities(subName : string) {
    let params = new HttpParams().append("subName",subName);
    return this.http.get<Array<City>>(`${environment.BackendServer}/cities`,{params});
  }

  getCitiesOrder(order : string , subName : string)  {
    let params  = new HttpParams()
      .append("order",order)
      .append("subName",subName);
    return this.http.get<Array<City>>(`${environment.BackendServer}/cities/filterCityByLibraries`,{params})
  }

  saveCity(newCity : City):Observable<City> {
    return this.http.post<City>(`${environment.BackendServer}/cities/addCity`,newCity);
  }
}
