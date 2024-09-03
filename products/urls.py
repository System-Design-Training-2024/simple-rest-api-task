# products/urls.py
from django.urls import path
from .views import ProductRetrieveView, ProductListView

urlpatterns = [
    path('products/', ProductListView.as_view(), name='product-list'),  # For listing all products
    path('products/<int:pk>/', ProductRetrieveView.as_view(), name='product-detail'),
]

from .views import  ProductCreateView, ProductUpdateView

urlpatterns = [
    path('products/get-all/', ProductListView.as_view(), name='product-list'),  # List and create
    path('products/create/', ProductCreateView.as_view(), name='create-product'),  # create
    path('products/update/<int:pk>/', ProductUpdateView.as_view(), name='update-product'),  # create
    path('products/get-one/<int:pk>/', ProductRetrieveView.as_view(), name='product-detail'),  # Retrieve and update
]