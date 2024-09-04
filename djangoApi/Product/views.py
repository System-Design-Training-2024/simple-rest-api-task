from itertools import product

from django.shortcuts import render

from django.http.response import JsonResponse
from rest_framework.parsers import JSONParser
from rest_framework import status

from Product.models import Product
from Product.serializers import ProductSerializer
from rest_framework.decorators import api_view

# Create your views here.
@api_view(['GET','POST'])
def Product_list(request):
    if (request.method=='GET'):
        allProduct=Product.objects.all()
        serializer = ProductSerializer(allProduct, many=True)
        return JsonResponse(serializer.data, safe=False)
    elif request.method == 'POST':
        Product_data = JSONParser().parse(request)
        Product_serializer = ProductSerializer(data=Product_data)
        if Product_serializer.is_valid():
            Product_serializer.save()
            return JsonResponse(Product_serializer.data)
        return JsonResponse(Product_serializer.errors, status=status.HTTP_404_NOT_FOUND)


@api_view(['GET','POST','PUT','DELETE'])
def Product_CRUD(request,pk):
    try:
        cur_product = Product.objects.get(pk=pk)
    except Product.DoesNotExist:
        return JsonResponse({'message': 'Product  does not exist'}, status=status.HTTP_404_NOT_FOUND)

    if (request.method=='GET'):

        Product_serializer = ProductSerializer(cur_product)
        return JsonResponse(Product_serializer.data)
    elif (request.method=="PUT"):
        Product_data = JSONParser().parse(request)
        Product_serializer = ProductSerializer(cur_product,data=Product_data)
        if Product_serializer.is_valid():
            Product_serializer.save()
            return JsonResponse(Product_serializer.data)
        return JsonResponse(Product_serializer.errors, status=status.HTTP_404_NOT_FOUND)

    elif request.method=="DELETE":
        cur_product.delete()
        return JsonResponse({"massege":"is done"}, status=status.HTTP_204_NO_CONTENT)




