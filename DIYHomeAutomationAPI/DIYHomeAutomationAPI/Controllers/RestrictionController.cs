using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RestrictionController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public RestrictionController(SensorDbContext context) => _context = context;

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Restriction>>> GetRooms() =>
            await _context.Restrictions.ToListAsync();
    }
}
