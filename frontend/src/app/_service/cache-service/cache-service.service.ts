import { Injectable } from '@angular/core';
import { CacheEstates } from '../../model/cacheEstates';

@Injectable({
  providedIn: 'root'
})
export class CacheService {
  readonly maxCacheSize:number= 5

  constructor() { }
  addPageCache(
    info:any, 
    pageCache:Map<string, any>, 
    params:{ [key: string]: any }
  ) {
    const cacheKey = this.getKey(params)
    pageCache.set(cacheKey, info);

    if(pageCache.size>this.maxCacheSize){
      const oldestKey = pageCache.keys().next().value;
      if(oldestKey)
        pageCache.delete(oldestKey);
      
    }
  }

  getKey(info:any):string{
    return JSON.stringify(info)
  }

  getFromCache(cacheKey: string, pageCache: Map<string, CacheEstates>): CacheEstates | undefined {
    return pageCache.get(cacheKey);
  }
  
}
