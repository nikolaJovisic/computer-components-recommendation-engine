import { Injectable, Query } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ComponentService {

  private url: string = "/components"
  constructor(private http: HttpClient) { }

  getComponent(componentType: string): Observable<string[]>{
    return this.http.get<string[]>(environment.host + this.url + `/${componentType}`);
  }

  getUpgradeRecommendation(componentType: string, currentComponentName: string, motherboard: string): Observable<string[]>{
    let upgradeQueryDto: UpgradeQueryDTO;
    upgradeQueryDto = {
      componentType: componentType,
      currentComponentName: currentComponentName,
      motherboard: motherboard
    }
    return this.http.post<string[]>(environment.host + this.url + '/upgrade',upgradeQueryDto);
  }

 
  
}

interface UpgradeQueryDTO{
  componentType: string;
  currentComponentName: string;
  motherboard: string;
}