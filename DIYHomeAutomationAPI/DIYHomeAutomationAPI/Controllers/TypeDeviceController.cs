using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TypeDeviceController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public TypeDeviceController(SensorDbContext context) => _context = context;

        [HttpGet]
        public async Task<ActionResult<IEnumerable<TypeDevice>>> GetRooms() =>
            await _context.TypeDevices.ToListAsync();
    }
}
