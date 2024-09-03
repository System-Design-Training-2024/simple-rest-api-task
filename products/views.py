# products/views.py

from django.views import View
from django.http import JsonResponse, HttpResponse
from django.views.generic.edit import CreateView
from .models import Product
from django.shortcuts import get_object_or_404
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator
import json
class ProductListView(View):
    def get(self, request):
        products = Product.objects.all()
        product_list = [
            {
                'id': product.id,
                'name': product.name,
                'description': product.description,
                'price': str(product.price),
                'created_at': product.created_at.isoformat(),
                'updated_at': product.updated_at.isoformat()
            }
            for product in products
        ]
        return JsonResponse(product_list, safe=False)


class ProductRetrieveView(View):
    def get(self, request, pk):
        product = get_object_or_404(Product, pk=pk)
        product_data = {
            'id': product.id,
            'name': product.name,
            'description': product.description,
            'price': str(product.price),
            'created_at': product.created_at.isoformat(),
            'updated_at': product.updated_at.isoformat()
        }
        return JsonResponse(product_data)


class ProductCreateView(View):
    def post(self, request):
        data = json.loads(request.body)
        name = data.get('name')
        description = data.get('description')
        price = data.get('price')

        if not name or not price:
            return JsonResponse({'error': 'Name and price are required.'}, status=400)

        product = Product.objects.create(
            name=name,
            description=description,
            price=price
        )

        product_data = {
            'id': product.id,
            'name': product.name,
            'description': product.description,
            'price': str(product.price),
            'created_at': product.created_at.isoformat(),
            'updated_at': product.updated_at.isoformat()
        }
        return JsonResponse(product_data, status=201)


# products/views.py
class ProductUpdateView(View):
    def put(self, request, pk):
        product = get_object_or_404(Product, pk=pk)
        data = json.loads(request.body)


        name = data.get('name')
        description = data.get('description')
        price = data.get('price')

        if name:
            product.name = name
        if description:
            product.description = description
        if price:
            product.price = price

        product.save()

        product_data = {
            'id': product.id,
            'name': product.name,
            'description': product.description,
            'price': str(product.price),
            'created_at': product.created_at.isoformat(),
            'updated_at': product.updated_at.isoformat()
        }
        return JsonResponse(product_data)




class ProductDeleteView(View):
    def delete(self, request, pk):
        if request.method == 'DELETE':
            product = get_object_or_404(Product, pk=pk)
            product.delete()
            return HttpResponse(status=204)  # Return 204 No Content to indicate success
        else:
            return JsonResponse({'error': 'Method not allowed'}, status=405)