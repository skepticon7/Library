import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {} from "cloudinary"

@Injectable({
  providedIn: 'root'
})
export class CloudinaryService {

  private cloudName = environment.cloudinaryCloudName;
  private uploadPreset = environment.cloudinaryPresetName;
  private apiKey = environment.apiKey;
  private apiSecret = environment.apiSecret;
  constructor(private http : HttpClient) {}

  getSignature() : Observable<any> {
      return this.http.get(`${environment.BackendServer}/cloudinary/getSignature`);
  }
  uploadImage(file : File , signature : string , timestamp : number) : Observable<any> {
    const formData = new FormData();
    formData.append('file',file);
    formData.append("cloud_name",this.cloudName);
    formData.append("signature",signature);
    formData.append("api_key",this.apiKey)
    formData.append("timestamp",timestamp.toString())

    const cloudinaryUrl = `https://api.cloudinary.com/v1_1/${this.cloudName}/image/upload`;

    return this.http.post(cloudinaryUrl , formData).pipe(
      catchError(err => {
        console.log("error uploading the image : " + err);
        return throwError(()=>new Error(err.message));
      })
    )
  }


  uploadFile(file : File , signature : string , timestamp : string) : Observable<any> {
    const formData = new FormData();
    formData.append("file",file);
    formData.append("cloud_name",this.cloudName);
    formData.append("signature",signature);
    formData.append("api_key",this.apiKey);
    formData.append("timestamp",timestamp.toString());
    return this.http.post(`${environment.BackendServer}/cloudinary/uploadFile`,formData).pipe(
      catchError(err => {
        console.log("error uploading the file : " + err.message);
        return throwError(()=>new Error(err.message));
      })
    )

  }

  deleteImage(publicId : string) : Observable<any> {
    const cloudinary_url = `https://api.cloudinary.com/v1_1/${this.cloudName}/image/destroy`;
    const formData = new FormData();
    formData.append("public_id",publicId);
    formData.append("api_key",this.apiKey);
    formData.append("api_secret",this.apiSecret);
    formData.append("upload_preset",this.uploadPreset);
    formData.append("cloud_name",this.cloudName);

    return this.http.post(cloudinary_url,formData).pipe(
      catchError(err => {
        console.log("error deleting image" , err);
        return throwError(()=>new Error(err.message));
      })
    )
  }

}
