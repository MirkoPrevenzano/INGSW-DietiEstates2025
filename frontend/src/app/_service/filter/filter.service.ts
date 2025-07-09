import { Injectable } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class FilterService {
  constructor(
    private readonly router: Router, 
    private readonly activatedRoute: ActivatedRoute
  ) {}

  updateUrl(params:Params, page: number, limit: number): void {
    const queryParams: Params = {
      ...params,
      page,
      limit
    };

    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams,
      queryParamsHandling: 'merge'
    });
  }

  retrieveFilter(params: Params): { filters: Params, page: number } {
    const page = Number(params['page']) || 0;
    const filters = { ...params };
    delete filters['page'];
    delete filters['limit'];
    return { filters, page };
  }
}