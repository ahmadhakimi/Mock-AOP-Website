import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  console.log('Auth Intercepter called');

  const currentUser = sessionStorage.getItem('currentUser');
  const token = currentUser ? JSON.parse(currentUser).token : null;

  console.log('Token :', token);

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log('Authorization Header: ', cloned.headers.get('Authorization'));

    return next(cloned);
  } else {
    console.log('No token found');
    return next(req);
  }
};
