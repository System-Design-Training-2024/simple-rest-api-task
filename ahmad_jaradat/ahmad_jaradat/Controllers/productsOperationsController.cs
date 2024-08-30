using ahmad_jaradat.DB;  
using ahmad_jaradat.Domain;  // For Product
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Threading.Tasks;


namespace ahmad_jaradat.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class productsOperationsController : ControllerBase
    {
        private readonly AppDbContext dbContext;

        public productsOperationsController(AppDbContext dbContext)
        {
            this.dbContext = dbContext;
        }

        [HttpPost]
        public IActionResult Create([FromBody] Product product)
        {
            product.id = Guid.NewGuid();

            product.createdAt = DateTime.UtcNow;
            product.UpdatedAt = DateTime.UtcNow;

            dbContext.Products.Add(product);
            dbContext.SaveChanges();

            return CreatedAtAction(nameof(GetById), new { id = product.id }, product);
        }


        [HttpGet]
        public IActionResult GetAll()
        {
            var products = dbContext.Products.ToList();
            return Ok(products);
        }

        [HttpGet]
        [Route("{id:Guid}")]
        public IActionResult GetById([FromRoute] Guid id)
        {
            var product = dbContext.Products.Find(id);

            if (product == null) 
            { 
                return NotFound();
            }
            return Ok(product);
        }


        [HttpPut("{id:Guid}")]
        public IActionResult Update([FromRoute] Guid id, [FromBody] Product updatedProduct)
        {
            var product = dbContext.Products.Find(id);

            if (product == null)
            {
                return NotFound();
            }

            product.name = updatedProduct.name;
            product.description = updatedProduct.description;
            product.price = updatedProduct.price;
            product.UpdatedAt = DateTime.UtcNow;

            dbContext.SaveChanges();

            return Ok(product);
        }

        [HttpDelete("{id:Guid}")]
        public IActionResult Delete([FromRoute] Guid id)
        {
            
            var product = dbContext.Products.Find(id);

            
            if (product == null)
            {
                return NotFound();
            }

            dbContext.Products.Remove(product);

            dbContext.SaveChanges();

            return NoContent();
        }

    }
}

