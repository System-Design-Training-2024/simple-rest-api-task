from Product import views
from django.urls import path

urlpatterns = [
    path('api/Product', views.Product_list),

    path('api/Product/<int:pk>', views.Product_CRUD),
]
