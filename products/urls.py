# products/urls.py
from django.urls import path
from .views import ProductRetrieveView, ProductListView

urlpatterns = [
    path('products/', ProductListView.as_view(), name='product-list'),  # For listing all products
    path('products/<int:pk>/', ProductRetrieveView.as_view(), name='product-detail'),
]

from .views import  ProductCreateView, ProductUpdateView

urlpatterns = [
    path('products/', ProductListView.as_view(), name='product-list'),  # List and create
    path('products/<int:pk>/', ProductRetrieveView.as_view(), name='product-detail'),  # Retrieve and update
]